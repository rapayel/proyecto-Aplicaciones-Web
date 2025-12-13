package Modelo.DAO;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos; // Reutilizamos Productos para el detalle
import Modelo.Entidades.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author deibiledesma
 */
public class VentasDAO {

    private final ConexionMySQL cn = new ConexionMySQL();

    
    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        // Hacemos JOIN con usuario para saber el nombre del cliente
        String sql = """
            SELECT v.id, v.fecha, v.total, u.NOMBRE_COMPLETO 
            FROM ventas v
            JOIN usuario u ON v.usuario_id = u.ID
            ORDER BY v.fecha DESC
        """;

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                v.setFecha(rs.getTimestamp("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setNombreUsuario(rs.getString("NOMBRE_COMPLETO"));
                
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error listarVentas: " + e.getMessage());
        }
        return lista;
    }

    
    public List<Productos> obtenerDetalleVenta(int idVenta) {
        List<Productos> detalle = new ArrayList<>();
        
        String sql = """
            SELECT p.producto, p.modelo, dv.cantidad, dv.precio_unitario, 
                   (dv.cantidad * dv.precio_unitario) as subtotal
            FROM detalle_venta dv
            JOIN productos p ON dv.producto_id = p.id
            WHERE dv.venta_id = ?
        """;

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Productos p = new Productos();
                p.setProducto(rs.getString("producto"));
                p.setModelo(rs.getString("modelo"));
                p.setCantidad_Stock(rs.getInt("cantidad")); // Reusamos este campo para la cantidad comprada
                p.setPrecio_venta(rs.getDouble("precio_unitario"));
               //en caso de subtotal p.setSubtotal(rs.getDouble("subtotal")); // Necesitamos un campo subtotal en Productos para este fin
                
                detalle.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerDetalleVenta: " + e.getMessage());
        }
        return detalle;
    }
       
    public String realizarVenta(int idUsuario) {
        String resultado = "";
        String sql = "{CALL sp_ComprarCarrito(?)}";

        try (Connection con = cn.conexion();
             CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);
            
            // Ejecutamos el procedimiento
            stmt.execute();
            
            // Si no lanza excepción, asumimos éxito
            resultado = "exito";

        } catch (SQLException e) {
            // Capturamos los errores que lanza el SP con SIGNAL SQLSTATE '45000'
            // Ejemplos: "No hay carrito", "Reservas expiradas", etc.
            System.err.println("Error en realizarVenta: " + e.getMessage());
            resultado = e.getMessage();
        }
        
        return resultado;
    }

/**
     * Elimina una venta completa y devuelve los productos al stock.
     * Se usa una transacción para asegurar que no se borre la venta si falla la devolución de stock.
     */
    public boolean eliminarVenta(int idVenta) {
        //  SQL para obtener qué productos se vendieron y cuántos
        String sqlSelectDetalle = "SELECT producto_id, cantidad FROM detalle_venta WHERE venta_id = ?";
        
        //  SQL para devolver el stock (CORREGIDO: 'Cantidad_Stock')
        String sqlRestaurarStock = "UPDATE productos SET Cantidad_Stock = Cantidad_Stock + ? WHERE id = ?";
        
        //  SQL para borrar detalles y cabecera
        String sqlDeleteDetalle = "DELETE FROM detalle_venta WHERE venta_id = ?";
        String sqlDeleteVenta = "DELETE FROM ventas WHERE id = ?";
        
        Connection con = null;
        
        try {
            con = cn.conexion();
            // IMPORTANTE: Inicio de transacción manual
            con.setAutoCommit(false);
            
            //  Recuperar los productos de esa venta para devolverlos al inventario
            try (PreparedStatement psSel = con.prepareStatement(sqlSelectDetalle)) {
                psSel.setInt(1, idVenta);
                try (ResultSet rs = psSel.executeQuery()) {
                    
                    // Preparamos el update del stock en lote (batch)
                    try (PreparedStatement psStock = con.prepareStatement(sqlRestaurarStock)) {
                        boolean hayProductos = false;
                        while(rs.next()){
                            int cant = rs.getInt("cantidad");
                            int idProd = rs.getInt("producto_id");
                            
                            // Cantidad a sumar, ID del producto
                            psStock.setInt(1, cant); 
                            psStock.setInt(2, idProd);
                            psStock.addBatch(); // Agregar al lote
                            hayProductos = true;
                        }
                        
                        // Si había productos, ejecutamos la devolución de stock
                        if (hayProductos) {
                            psStock.executeBatch();
                        }
                    }
                }
            }
            
            // P Eliminar los detalles de la venta 
            try (PreparedStatement psDelDet = con.prepareStatement(sqlDeleteDetalle)) {
                psDelDet.setInt(1, idVenta);
                psDelDet.executeUpdate();
            }

            //  Eliminar la cabecera de la venta
            try (PreparedStatement psDelVenta = con.prepareStatement(sqlDeleteVenta)) {
                psDelVenta.setInt(1, idVenta);
                int filas = psDelVenta.executeUpdate();
                
                if (filas == 0) {
                    throw new SQLException("No se encontró la venta con ID: " + idVenta);
                }
            }
            
            // Si todo salió bien, guardamos los cambios
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error eliminarVenta: " + e.getMessage());
            // Si algo falló, deshacemos todo (Rollback)
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { System.err.println("Error Rollback"); }
            }
            return false;
        } finally {
            // Restaurar estado y cerrar conexión
            if (con != null) {
                try { 
                    con.setAutoCommit(true); 
                    con.close(); 
                } catch (SQLException ex) { System.err.println("Error Close"); }
            }
        }
    }
}