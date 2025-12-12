package Controlador;

import Modelo.DAO.ProductosDAO;
import Modelo.DAO.CarritoDAO;
import Modelo.Entidades.Productos;
import java.io.IOException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ControladorPrincipal", urlPatterns = {"/ControladorPrincipal"})
public class ControladorPrincipal extends HttpServlet {

    private final ProductosDAO productosDAO = new ProductosDAO();
    private final CarritoDAO carritoDAO = new CarritoDAO(); // ✅ NUEVO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarProductos(request, response);
                break;
            case "verProducto":
                verProducto(request, response);
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

    // ---------------------------------------------------------
    // LISTAR PRODUCTOS
    // ---------------------------------------------------------
    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Productos> lista = productosDAO.listarProductos();

        request.setAttribute("productos", lista);
        request.getRequestDispatcher("Principal.jsp").forward(request, response);
    }

    // ---------------------------------------------------------
    // AGREGAR AL CARRITO (✅ CORREGIDO)
    // ---------------------------------------------------------
    private void agregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Object idObj = session.getAttribute("idUsuario");
        if (idObj == null) {
            response.sendRedirect("InicioSesion.html");
            return;
        }

        int idUsuario = (int) idObj;
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidadSolicitada = Integer.parseInt(request.getParameter("Cantidad_Stock"));

        boolean ok = productosDAO.agregarAlCarrito(idUsuario, idProducto, cantidadSolicitada);

        if (ok) {
            // ✅ ACTUALIZAR CARRITO EN SESIÓN
            List<Productos> carritoActualizado = carritoDAO.obtenerCarrito(idUsuario);
            session.setAttribute("carrito", carritoActualizado);

            request.setAttribute("mensaje", "✅ Producto agregado correctamente al carrito.");
        } else {
            request.setAttribute("mensaje", "❌ Error al agregar el producto al carrito.");
        }

        listarProductos(request, response);
    }

    // ---------------------------------------------------------
    // VER DETALLES DEL PRODUCTO
    // ---------------------------------------------------------
    private void verProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Productos prod = productosDAO.obtenerProducto(id);
        request.setAttribute("producto", prod);
        request.getRequestDispatcher("detalleProducto.jsp").forward(request, response);
    }
}