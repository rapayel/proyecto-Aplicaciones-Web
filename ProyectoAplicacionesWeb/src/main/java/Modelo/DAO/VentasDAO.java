package Modelo.DAO;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos;
import Modelo.Entidades.Venta;
import Modelo.Entidades.VentaDetalleCompleta;
import Modelo.Entidades.GananciaMes;
import Modelo.Entidades.VentaMes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentasDAO {
    private final ConexionMySQL cn = new ConexionMySQL();

    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
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
                p.setCantidad_Stock(rs.getInt("cantidad"));
                p.setPrecio_venta(rs.getDouble("precio_unitario"));
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
            stmt.execute();
            resultado = "exito";
        } catch (SQLException e) {
            System.err.println("Error en realizarVenta: " + e.getMessage());
            resultado = e.getMessage();
        }
        return resultado;
    }

    public boolean eliminarVenta(int idVenta) {
        String sqlSelectDetalle = "SELECT producto_id, cantidad FROM detalle_venta WHERE venta_id = ?";
        String sqlRestaurarStock = "UPDATE productos SET Cantidad_Stock = Cantidad_Stock + ? WHERE id = ?";
        String sqlDeleteDetalle = "DELETE FROM detalle_venta WHERE venta_id = ?";
        String sqlDeleteVenta = "DELETE FROM ventas WHERE id = ?";

        Connection con = null;
        try {
            con = cn.conexion();
            con.setAutoCommit(false);

            try (PreparedStatement psSel = con.prepareStatement(sqlSelectDetalle)) {
                psSel.setInt(1, idVenta);
                try (ResultSet rs = psSel.executeQuery();
                     PreparedStatement psStock = con.prepareStatement(sqlRestaurarStock)) {
                    boolean hayProductos = false;
                    while (rs.next()) {
                        int cant = rs.getInt("cantidad");
                        int idProd = rs.getInt("producto_id");
                        psStock.setInt(1, cant);
                        psStock.setInt(2, idProd);
                        psStock.addBatch();
                        hayProductos = true;
                    }
                    if (hayProductos) {
                        psStock.executeBatch();
                    }
                }
            }

            try (PreparedStatement psDelDet = con.prepareStatement(sqlDeleteDetalle)) {
                psDelDet.setInt(1, idVenta);
                psDelDet.executeUpdate();
            }

            try (PreparedStatement psDelVenta = con.prepareStatement(sqlDeleteVenta)) {
                psDelVenta.setInt(1, idVenta);
                int filas = psDelVenta.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se encontró la venta con ID: " + idVenta);
                }
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error eliminarVenta: " + e.getMessage());
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { System.err.println("Error Rollback"); }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) { System.err.println("Error Close"); }
            }
        }
    }

    // === TOTAL VENTAS DEL MES (INGRESOS) ===
    public double obtenerTotalVentasMes() {
        String sql = "{CALL sp_TotalVentasMes()}";
        double total = 0.0;
        try (Connection con = cn.conexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("ingresos"); // ← Usa el alias "ingresos" de tu SP
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    public List<VentaMes> obtenerVentasUltimos12Meses() {
    List<VentaMes> lista = new ArrayList<>();
    String sql = "{CALL sp_VentasPorMes()}";

    try (Connection con = cn.conexion();
         CallableStatement cs = con.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {

        while (rs.next()) {
            VentaMes vm = new VentaMes();
            vm.setAnio(rs.getInt("anio"));
            vm.setMes(rs.getInt("mes"));
            vm.setTotalVentas(rs.getInt("total_ventas"));
            lista.add(vm);
        }
    } catch (SQLException e) {
        System.err.println("Error obtenerVentasUltimos12Meses: " + e.getMessage());
    }
    return lista;
}

    public List<Integer> obtenerVentasPorMes() {
        List<Integer> ventas = new ArrayList<>();
        String sql = "{CALL sp_VentasPorMes()}";
        try (Connection con = cn.conexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ventas.add(rs.getInt("total_ventas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ventas;
    }

    public List<VentaDetalleCompleta> obtenerTodasLasVentasConDetalle() {
        List<VentaDetalleCompleta> lista = new ArrayList<>();
        String sql = "{CALL sp_TodasLasVentas()}";
        try (Connection con = cn.conexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                VentaDetalleCompleta v = new VentaDetalleCompleta();
                v.setIdVenta(rs.getInt("venta_id"));
                v.setFecha(rs.getTimestamp("fecha_venta"));
                v.setIdUsuario(rs.getInt("usuario_id"));
                v.setNombreCompleto(rs.getString("nombre_completo"));
                v.setNombreUsuario(rs.getString("nombre_usuario"));
                v.setIdProducto(rs.getInt("producto_id"));
                v.setProducto(rs.getString("producto"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setCantidad(rs.getInt("cantidad"));
                v.setPrecioUnitario(rs.getDouble("precio_unitario"));
                v.setTotalLinea(rs.getDouble("total_linea"));
                v.setTotalVenta(rs.getDouble("total_venta"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerTodasLasVentasConDetalle: " + e.getMessage());
        }
        return lista;
    }

    // === GANANCIAS ÚLTIMOS 6 MESES ===
    public List<GananciaMes> obtenerGananciasUltimos6Meses() {
        List<GananciaMes> lista = new ArrayList<>();
        String sql = "{CALL sp_GananciasUltimos6Meses()}";

        try (Connection con = cn.conexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                GananciaMes gm = new GananciaMes();
                gm.setAnio(rs.getInt("anio"));
                gm.setMes(rs.getInt("mes"));
                gm.setGanancia(rs.getDouble("ganancia"));
                lista.add(gm);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerGananciasUltimos6Meses: " + e.getMessage());
        }
        return lista;
    }
}