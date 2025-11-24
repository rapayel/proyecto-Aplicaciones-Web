/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.DAO.CarritoDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Modelo.Entidades.Productos;
import java.io.IOException;
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

    private final CarritoDAO carritoDAO = new CarritoDAO();

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

    // -------------------------------------------------------------
    // VER CARRITO
    // -------------------------------------------------------------
    private void verCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object idObj = session.getAttribute("idUsuario");

        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        int idUsuario = (int) idObj;

        List<Productos> carrito = carritoDAO.obtenerCarrito(idUsuario);

        double total = carrito.stream()
                .mapToDouble(p -> p.getPrecio_venta() * p.getCantidad_Stock())
                .sum();

        request.setAttribute("carrito", carrito);
        request.setAttribute("total", total);

        request.getRequestDispatcher("Carrito.jsp").forward(request, response);
    }

    // -------------------------------------------------------------
    // ELIMINAR PRODUCTO
    // -------------------------------------------------------------
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        boolean ok = carritoDAO.eliminarProducto(idUsuario, idProducto);

        if (ok) {
            request.setAttribute("mensaje", "Producto eliminado del carrito.");
        } else {
            request.setAttribute("mensaje", "❌ No se pudo eliminar el producto.");
        }

        verCarrito(request, response);
    }

    // -------------------------------------------------------------
    // FINALIZAR COMPRA
    // -------------------------------------------------------------
    private void finalizarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        boolean ok = carritoDAO.finalizarCompra(idUsuario);

        if (ok) {
            request.setAttribute("mensaje", "✔ Compra finalizada correctamente.");
        } else {
            request.setAttribute("mensaje", "⚠ No había productos activos en el carrito.");
        }

        verCarrito(request, response);
    }
}
