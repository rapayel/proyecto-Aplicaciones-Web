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

    // -------------------------------------------------------------
    // LISTAR CARRITO
    // -------------------------------------------------------------
    public List<Productos> obtenerCarrito(int idUsuario) {

        List<Productos> carrito = new ArrayList<>();

        String sql = """
            SELECT p.id, p.producto, p.descripcion, p.precioVenta, pc.cantidad,
                   (p.precioVenta * pc.cantidad) AS subtotal
            FROM producto_carrito pc
            JOIN carrito c ON c.id = pc.carrito_id
            JOIN productos p ON p.id = pc.producto_id
            WHERE c.usuario_id = ? AND pc.estado_reserva = 'ACTIVA';
        """;

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Productos p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
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

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error DAO eliminarProducto: " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------------------------
    // FINALIZAR COMPRA
    // -------------------------------------------------------------
    public boolean finalizarCompra(int idUsuario) {

        String sql = """
            UPDATE producto_carrito pc
            JOIN carrito c ON c.id = pc.carrito_id
            SET pc.estado_reserva = 'FINALIZADA'
            WHERE c.usuario_id = ? AND pc.estado_reserva = 'ACTIVA';
        """;

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error DAO finalizarCompra: " + e.getMessage());
            return false;
        }
    }
}
