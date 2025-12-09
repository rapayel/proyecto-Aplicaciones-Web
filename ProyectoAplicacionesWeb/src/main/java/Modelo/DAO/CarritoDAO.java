/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

/**
 *
 * @author Arell
 */

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAO {

    private final ConexionMySQL cn = new ConexionMySQL();
    
    // --- MÉTODOS DE SOPORTE PARA EL CHECKOUT ---
    
    private int obtenerOCrearCarritoId(int idUsuario, Connection con) throws SQLException {
        String sqlSelect = "SELECT id FROM carrito WHERE usuario_id = ?"; 
        try (PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
            psSelect.setInt(1, idUsuario);
            ResultSet rs = psSelect.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id"); 
            } else {
                String sqlInsert = "INSERT INTO carrito (usuario_id) VALUES (?)";
                try (PreparedStatement psInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    psInsert.setInt(1, idUsuario);
                    psInsert.executeUpdate();
                    
                    ResultSet rsKeys = psInsert.getGeneratedKeys();
                    if (rsKeys.next()) {
                        return rsKeys.getInt(1); 
                    } else {
                        throw new SQLException("Error al crear el ID del carrito.");
                    }
                }
            }
        }
    }
    
    // --- NUEVO: AÑADIR O ACTUALIZAR PRODUCTO (usado por agregarProducto) ---

    public boolean agregarOActualizarProducto(int idUsuario, int idProducto, int cantidad) {
        Connection con = null;
        try {
            con = cn.conexion();
            con.setAutoCommit(false); 
            int carritoId = obtenerOCrearCarritoId(idUsuario, con);

            String sqlCheck = "SELECT cantidad FROM producto_carrito WHERE carrito_id = ? AND producto_id = ? AND estado_reserva = 'ACTIVA'";
            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, carritoId);
                psCheck.setInt(2, idProducto);
                ResultSet rs = psCheck.executeQuery();

                if (rs.next()) {
                    int cantidadActual = rs.getInt("cantidad");
                    int nuevaCantidad = cantidadActual + cantidad;
                    
                    String sqlUpdate = "UPDATE producto_carrito SET cantidad = ? WHERE carrito_id = ? AND producto_id = ? AND estado_reserva = 'ACTIVA'";
                    try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                        psUpdate.setInt(1, nuevaCantidad);
                        psUpdate.setInt(2, carritoId);
                        psUpdate.setInt(3, idProducto);
                        psUpdate.executeUpdate();
                    }
                } else {
                    String sqlInsert = "INSERT INTO producto_carrito (carrito_id, producto_id, cantidad, estado_reserva) VALUES (?, ?, ?, 'ACTIVA')";
                    try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                        psInsert.setInt(1, carritoId);
                        psInsert.setInt(2, idProducto);
                        psInsert.setInt(3, cantidad);
                        psInsert.executeUpdate();
                    }
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error DAO agregarOActualizarProducto: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) { /* Ignorar */ }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) { /* Ignorar */ }
            }
        }
    }
    
    // --- NUEVO: MODIFICAR CANTIDAD (usado por modificarCantidad) ---

    public boolean modificarCantidad(int idUsuario, int idProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return eliminarProducto(idUsuario, idProducto);
        }
        
        String sql = """
            UPDATE producto_carrito pc
            JOIN carrito c ON c.id = pc.carrito_id
            SET pc.cantidad = ?
            WHERE c.usuario_id = ? AND pc.producto_id = ? AND pc.estado_reserva = 'ACTIVA';
        """;

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error DAO modificarCantidad: " + e.getMessage());
            return false;
        }
    }

    // --- NUEVO: PROCESAR VENTA COMPLETA (usado por confirmarPedido) ---

public String procesarVentaCompleta(int idUsuario, String direccion, String metodoPago) {
    Connection con = null;
    try {
        con = cn.conexion();
        con.setAutoCommit(false); 

        List<Productos> carrito = obtenerCarrito(idUsuario);
        if (carrito.isEmpty()) {
            con.rollback();
            return null;
        }

        double totalVenta = carrito.stream()
            .mapToDouble(p -> p.getPrecio_venta() * p.getCantidad_Stock())
            .sum();

        // PASO 1: Insertar en la tabla 'ventas'
        // *** SQL MODIFICADO: Solo inserta usuario_id, fecha, y total ***
        String sqlVenta = "INSERT INTO ventas (usuario_id, fecha, total) VALUES (?, NOW(), ?)";
        int ventaId = -1;
        try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            psVenta.setInt(1, idUsuario);
            psVenta.setDouble(2, totalVenta);
            // NOTA: No se establecen parámetros 3 y 4 (dirección/pago) para evitar errores de BD.
            psVenta.executeUpdate();

            ResultSet rsKeys = psVenta.getGeneratedKeys();
            if (rsKeys.next()) {
                ventaId = rsKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID de la Venta.");
            }
        }
        
        // PASO 2: Insertar en la tabla 'detalle_venta'
        String sqlDetalle = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
            for (Productos p : carrito) {
                psDetalle.setInt(1, ventaId);
                psDetalle.setInt(2, p.getId());
                psDetalle.setInt(3, p.getCantidad_Stock());
                psDetalle.setDouble(4, p.getPrecio_venta());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
        }

        // PASO 3: Actualizar el estado del carrito a FINALIZADA
        String sqlUpdateCarrito = """
            UPDATE producto_carrito pc
            JOIN carrito c ON c.id = pc.carrito_id
            SET pc.estado_reserva = 'FINALIZADA'
            WHERE c.usuario_id = ? AND pc.estado_reserva = 'ACTIVA';
        """;
        try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateCarrito)) {
            psUpdate.setInt(1, idUsuario);
            psUpdate.executeUpdate();
        }

        con.commit();
        return String.valueOf(ventaId);

    } catch (SQLException e) {
        System.out.println("❌ Error DAO procesarVentaCompleta: " + e.getMessage());
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) { /* Ignorar */ }
        }
        return null;
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException ex) { /* Ignorar */ }
        }
    }
}
    
   public List<Productos> obtenerCarrito(int idUsuario) {

        List<Productos> carrito = new ArrayList<>();

        // La columna 'cantidad' se obtiene de 'producto_carrito' (pc)
        // Usamos el estado_reserva = 'ACTIVA' como filtro
        String sql = """
        SELECT p.id, p.producto, p.descripcion, p.precioVenta, pc.cantidad
        FROM producto_carrito pc
        JOIN carrito c ON c.id = pc.carrito_id
        JOIN productos p ON p.id = pc.producto_id
        WHERE c.usuario_id = ? AND pc.estado_reserva = 'ACTIVA';
    """;

        try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Productos p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
                // Importante: Usamos el setter de Cantidad_Stock para almacenar la CANTIDAD DEL CARRITO
                p.setCantidad_Stock(rs.getInt("cantidad"));
                carrito.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error DAO obtenerCarrito: " + e.getMessage());
        }

        return carrito;
    }
   


    // -------------------------------------------------------------
    // ELIMINAR PRODUCTO DEL CARRITO
    // -------------------------------------------------------------
    public boolean eliminarProducto(int idUsuario, int idProducto) {

        String sql = """
            DELETE pc FROM producto_carrito pc
            JOIN carrito c ON c.id = pc.carrito_id
            WHERE c.usuario_id = ? AND pc.producto_id = ?;
        """;

        try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error DAO eliminarProducto: " + e.getMessage());
            return false;
        }
    }

  

}
