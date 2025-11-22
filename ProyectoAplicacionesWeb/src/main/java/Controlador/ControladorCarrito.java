/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Conexiones.ConexionMySQL;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Modelo.Entidades.Productos;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
/**
 *
 * @author Arell
 */
@WebServlet(name = "ControladorCarrito", urlPatterns = {"/ControladorCarrito"})
public class ControladorCarrito extends HttpServlet {
private final ConexionMySQL cn = new ConexionMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "verCarrito";

        switch (accion) {
            case "verCarrito":
                verCarrito(request, response);
                break;
            case "eliminarProducto":
                eliminarProducto(request, response);
                break;
            default:
                verCarrito(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("finalizarCompra".equals(accion)) {
            finalizarCompra(request, response);
        } else {
            verCarrito(request, response);
        }
    }

    private void verCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object idObj = session.getAttribute("idUsuario");
        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        int idUsuario = (int) idObj;
        List<Productos> carrito = new ArrayList<>();
        double total = 0;

        try (Connection con = cn.conexion()) {
            String sql = """
                SELECT p.id, p.producto, p.descripcion, p.precioVenta, pc.cantidad,
                       (p.precioVenta * pc.cantidad) AS subtotal
                FROM producto_carrito pc
                JOIN carrito c ON c.id = pc.carrito_id
                JOIN productos p ON p.id = pc.producto_id
                WHERE c.usuario_id = ? AND pc.estado_reserva = 'ACTIVA';
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Productos p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
                p.setCantidad_Stock(rs.getInt("cantidad"));
                total += p.getPrecio_venta() * p.getCantidad_Stock();
                carrito.add(p);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Error al listar carrito: " + e.getMessage());
        }

        request.setAttribute("carrito", carrito);
        request.setAttribute("total", total);
        request.getRequestDispatcher("Carrito.jsp").forward(request, response);
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        try (Connection con = cn.conexion()) {
            String sql = """
                DELETE pc FROM producto_carrito pc
                JOIN carrito c ON c.id = pc.carrito_id
                WHERE c.usuario_id = ? AND pc.producto_id = ?;
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);
            int filas = ps.executeUpdate();

            if (filas > 0)
                request.setAttribute("mensaje", "Producto eliminado del carrito.");
            else
                request.setAttribute("mensaje", "No se encontró el producto en el carrito.");

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "❌ Error al eliminar producto: " + e.getMessage());
        }

        verCarrito(request, response);
    }

    private void finalizarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        try (Connection con = cn.conexion()) {
            String sql = """
                UPDATE producto_carrito pc
                JOIN carrito c ON c.id = pc.carrito_id
                SET pc.estado_reserva = 'FINALIZADA'
                WHERE c.usuario_id = ? AND pc.estado_reserva = 'ACTIVA';
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            int filas = ps.executeUpdate();

            if (filas > 0)
                request.setAttribute("mensaje", "✅ Compra finalizada correctamente.");
            else
                request.setAttribute("mensaje", "⚠️ No había productos activos en el carrito.");

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "❌ Error al finalizar compra: " + e.getMessage());
        }

        verCarrito(request, response);
    }
}
