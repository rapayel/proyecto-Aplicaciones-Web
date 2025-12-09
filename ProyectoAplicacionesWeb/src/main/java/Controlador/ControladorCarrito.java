/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.util.List;

import Modelo.DAO.CarritoDAO;
import Modelo.DAO.VentasDAO;
import Modelo.Entidades.Productos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author Arell
 */

@WebServlet(name = "ControladorCarrito", urlPatterns = {"/ControladorCarrito"})
public class ControladorCarrito extends HttpServlet {

    private final CarritoDAO carritoDAO = new CarritoDAO();
    private final VentasDAO ventasDAO = new VentasDAO(); // Instanciamos VentasDAO

    // Maneja: Ver Carrito y Eliminar Producto
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if (accion == null) accion = "verCarrito";

        switch (accion) {
            case "agregarProducto":
                agregarProducto(request, response);
                break;
            case "modificarCantidad":
                modificarCantidad(request, response);
                break;
            case "verCarrito":
                mostrarCarrito(request, response);
                break;
            case "eliminarProducto":
                eliminarProducto(request, response);
                break;
            default:
                mostrarCarrito(request, response);
                break;
        }
    }

    // Maneja: Finalizar Compra
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");

        if ("iniciarCheckout".equals(accion)) { 
            iniciarCheckout(request, response);
        } else if ("confirmarPedido".equals(accion)) { 
            confirmarPedido(request, response);
        } else {
            mostrarCarrito(request, response);
        }
    }

    private void mostrarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Object idObj = session.getAttribute("idUsuario");
        
        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        int idUsuario = (int) idObj;
        List<Productos> listaCarrito = carritoDAO.obtenerCarrito(idUsuario);

        // Calcular total en Java (o podrías traerlo de BD)
        double total = 0;
        for (Productos p : listaCarrito) {
            total += p.getPrecio_venta() * p.getCantidad_Stock();
        }

        request.setAttribute("carrito", listaCarrito);
        request.setAttribute("total", total);
        request.getRequestDispatcher("Carrito.jsp").forward(request, response);
    }
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idUsuario = (int) request.getSession().getAttribute("idUsuario");
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        carritoDAO.eliminarProducto(idUsuario, idProducto);
        
        // Recargar vista del carrito
        mostrarCarrito(request, response);
    }

    private void finalizarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idUsuario = (int) request.getSession().getAttribute("idUsuario");

        String resultado = ventasDAO.realizarVenta(idUsuario);

        if ("exito".equals(resultado)) {
            // Redirigir a la pantalla de pago o éxito
            response.sendRedirect("exitosos.jsp"); 
        } else {
            request.setAttribute("mensaje", "Error: " + resultado);
            mostrarCarrito(request, response);
        }
    }
}
