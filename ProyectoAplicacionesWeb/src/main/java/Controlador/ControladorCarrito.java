package Controlador;

import Modelo.DAO.CarritoDAO;
import Modelo.Entidades.Productos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            case "eliminarProducto":
                eliminarProducto(request, response);
                break;
            case "verCarrito":
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


    private void verCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        List<Productos> carrito = carritoDAO.obtenerCarrito(idUsuario);

        double total = carrito.stream()
                .mapToDouble(p -> p.getPrecio_venta() * p.getCantidad_Stock())
                .sum();

        session.setAttribute("carrito", carrito);
        request.setAttribute("carrito", carrito);
        request.setAttribute("total", total);

        request.getRequestDispatcher("Carrito.jsp").forward(request, response);
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int cantidad = 1;

            String cantidadStr = request.getParameter("cantidad");
            if (cantidadStr != null && !cantidadStr.isEmpty()) {
                cantidad = Integer.parseInt(cantidadStr);
            }

            carritoDAO.agregarOActualizarProducto(idUsuario, idProducto, cantidad);

        } catch (Exception e) {
            System.out.println("❌ Error agregarProducto: " + e.getMessage());
        }

        verCarrito(request, response);
    }


    private void modificarCantidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            carritoDAO.modificarCantidad(idUsuario, idProducto, cantidad);

        } catch (Exception e) {
            System.out.println("❌ Error modificarCantidad: " + e.getMessage());
        }

        verCarrito(request, response);
    }

    
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            carritoDAO.eliminarProducto(idUsuario, idProducto);
        } catch (Exception e) {
            System.out.println("❌ Error eliminarProducto: " + e.getMessage());
        }

        verCarrito(request, response);
    }

    private void iniciarCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        List<Productos> carrito = carritoDAO.obtenerCarrito(idUsuario);

        if (carrito.isEmpty()) {
            request.setAttribute("mensaje", "⚠ El carrito está vacío.");
            verCarrito(request, response);
            return;
        }

        double total = carrito.stream()
                .mapToDouble(p -> p.getPrecio_venta() * p.getCantidad_Stock())
                .sum();

        session.setAttribute("carrito", carrito);
        request.setAttribute("carrito", carrito);
        request.setAttribute("total", total);

        request.getRequestDispatcher("ProcesoCompra.jsp").forward(request, response);
    }


    private void confirmarPedido(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        String direccionEnvio = request.getParameter("direccionEnvio");
        String metodoPago = request.getParameter("metodoPago");

        String numPedido = carritoDAO.procesarVentaCompleta(idUsuario, direccionEnvio, metodoPago);

        if (numPedido != null) {

           
            session.setAttribute("carrito", new ArrayList<>());

            request.setAttribute("numPedido", numPedido);
            request.getRequestDispatcher("Confirmacion.jsp").forward(request, response);

        } else {
            request.setAttribute("mensaje", "❌ Error al procesar la compra.");
            verCarrito(request, response);
        }
    }
}


