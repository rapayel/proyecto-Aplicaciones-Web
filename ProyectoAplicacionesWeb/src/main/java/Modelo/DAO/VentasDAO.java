package Modelo.DAO;

import java.sql.CallableStatement;
import java.sql.Connection; // Reutilizamos Productos para el detalle
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos;
import Modelo.Entidades.Venta;

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

        try (Connection con = cn.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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

        try (Connection con = cn.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

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

    // Método para ejecutar la compra usando el SP de la base de datos
    public String realizarVenta(int idUsuario) {
        String mensaje = "";
        String sql = "{CALL sp_ComprarCarrito(?)}";

        try (Connection con = cn.conexion(); CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.execute(); // Ejecuta el SP
            mensaje = "exito"; // Si no falla, asumimos éxito

        } catch (SQLException e) {
            // Aquí capturamos ese mensaje para mostrárselo al usuario
            mensaje = e.getMessage();
            System.err.println("Error realizarVenta: " + e.getMessage());
        }
        return mensaje;
    }
}
