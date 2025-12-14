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
            case "agregarProducto":
                agregarProducto(request, response);
                break;
            case "modificarCantidad":
                modificarCantidad(request, response);
                break;
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

        if ("iniciarCheckout".equals(accion)) { 
            iniciarCheckout(request, response);
        } else if ("confirmarPedido".equals(accion)) { 
            confirmarPedido(request, response);
        } else {
            verCarrito(request, response);
        }
    }

    // MÃ‰TODOS BASE (verCarrito, eliminarProducto)
    // El mÃ©todo verCarrito debe seguir en tu clase
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
    
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        boolean ok = carritoDAO.eliminarProducto(idUsuario, idProducto);

        if (ok) {
            request.setAttribute("mensaje", "Producto eliminado del carrito.");
        } else {
            request.setAttribute("mensaje", "âŒ No se pudo eliminar el producto.");
        }

        verCarrito(request, response);
    }

    // MÃ‰TODOS DE MANEJO DEL CARRITO (agregarProducto, modificarCantidad)
    
    private void agregarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object idObj = session.getAttribute("idUsuario");

        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }
        
        try {
            int idUsuario = (int) idObj;
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int cantidad = 1; 
            
            String cantidadStr = request.getParameter("cantidad");
            if (cantidadStr != null && !cantidadStr.isEmpty()) {
                cantidad = Integer.parseInt(cantidadStr);
            }
            
            boolean ok = carritoDAO.agregarOActualizarProducto(idUsuario, idProducto, cantidad);

            if (ok) {
                request.setAttribute("mensaje", "âœ” Producto aÃ±adido al carrito.");
            } else {
                request.setAttribute("mensaje", "âŒ No se pudo aÃ±adir el producto.");
            }

        } catch (NumberFormatException | NullPointerException e) {
            request.setAttribute("mensaje", "âŒ Error de parÃ¡metros al aÃ±adir producto.");
        }
        verCarrito(request, response); 
    }
    
    private void modificarCantidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Object idObj = session.getAttribute("idUsuario");

        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }
        
        try {
            int idUsuario = (int) idObj;
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int nuevaCantidad = Integer.parseInt(request.getParameter("cantidad"));
            
            boolean ok = carritoDAO.modificarCantidad(idUsuario, idProducto, nuevaCantidad);

            if (ok) {
                request.setAttribute("mensaje", "Cantidad actualizada.");
            } else {
                request.setAttribute("mensaje", "âŒ No se pudo modificar la cantidad.");
            }

        } catch (NumberFormatException | NullPointerException e) {
            request.setAttribute("mensaje", "âŒ Error de parÃ¡metros al modificar cantidad.");
        }
        
        verCarrito(request, response);
    }

    // MÃ‰TODOS DEL CHECKOUT (iniciarCheckout, confirmarPedido)

    private void iniciarCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object idObj = session.getAttribute("idUsuario");
        
        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        int idUsuario = (int) idObj;
        List<Productos> carrito = carritoDAO.obtenerCarrito(idUsuario);

        if (carrito.isEmpty()) {
            request.setAttribute("mensaje", "âš  No puedes finalizar la compra con el carrito vacÃ­o.");
            verCarrito(request, response);
            return;
        }
        
        double total = carrito.stream()
                .mapToDouble(p -> p.getPrecio_venta() * p.getCantidad_Stock())
                .sum();

        request.setAttribute("carrito", carrito);
        request.setAttribute("total", total);
        
        request.getRequestDispatcher("ProcesoCompra.jsp").forward(request, response);
    }
    
    private void confirmarPedido(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        // 1. Obtener datos (se leen, pero se ignoran en el DAO)
        String direccionEnvio = request.getParameter("direccionEnvio");
        String metodoPago = request.getParameter("metodoPago");
        
        // 2. Ejecutar la transacciÃ³n de venta (El DAO ignora los parÃ¡metros)
        String numPedido = carritoDAO.procesarVentaCompleta(idUsuario, direccionEnvio, metodoPago);

        if (numPedido != null) {
            session.setAttribute("carrito", new ArrayList<>());
            enviarCorreoConfirmacion(idUsuario, numPedido, direccionEnvio); 
                 
            request.setAttribute("numPedido", numPedido);
            request.getRequestDispatcher("Confirmacion.jsp").forward(request, response);
            
        } else {
            request.setAttribute("mensaje", "âŒ Error al procesar la compra. Intente de nuevo.");
            verCarrito(request, response);
        }
    }
    
    private void enviarCorreoConfirmacion(int idUsuario, String numPedido, String direccion) {
        // LÃ³gica de simulaciÃ³n para cumplir el requisito de notificar
        System.out.println("ðŸ“© Enviando confirmaciÃ³n de pedido " + numPedido + " al usuario " + idUsuario);
    }
}


