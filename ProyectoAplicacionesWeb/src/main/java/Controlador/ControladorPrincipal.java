package Controlador;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ControladorPrincipal", urlPatterns = {"/ControladorPrincipal"})
public class ControladorPrincipal extends HttpServlet {
    private final ConexionMySQL cn = new ConexionMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarProductos(request, response);
                break;
            default:
                listarProductos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("agregarCarrito".equals(accion)) {
            agregarCarrito(request, response);
        } else {
            listarProductos(request, response);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Productos> lista = new ArrayList<>();

        try (Connection con = cn.conexion()) {
            CallableStatement stmt = con.prepareCall("{CALL sp_listarProductos()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Productos p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_compra(rs.getDouble("precioCompra"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
                p.setCantidad_Stock(rs.getInt("Cantidad_Stock"));
                lista.add(p);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Error al listar productos: " + e.getMessage());
        }

        request.setAttribute("productos", lista);
        request.getRequestDispatcher("Principal.jsp").forward(request, response);
    }

    private void agregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidadSolicitada = Integer.parseInt(request.getParameter("Cantidad_Stock"));

        try (Connection con = cn.conexion()) {
            CallableStatement stmt = con.prepareCall("{CALL sp_AgregarProductoCarrito(?,?,?)}");
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidadSolicitada);

            try {
                stmt.execute();
                request.setAttribute("mensaje", "✅ Producto agregado correctamente al carrito.");
            } catch (SQLException ex) {
                request.setAttribute("mensaje", "⚠️ " + ex.getMessage());
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "❌ Error al agregar producto al carrito: " + e.getMessage());
        }

        listarProductos(request, response);
    }
}
