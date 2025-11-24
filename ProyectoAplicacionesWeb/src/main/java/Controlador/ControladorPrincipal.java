package Controlador;

import Modelo.DAO.ProductosDAO;
import Modelo.Entidades.Productos;
import java.io.IOException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ControladorPrincipal", urlPatterns = {"/ControladorPrincipal"})
public class ControladorPrincipal extends HttpServlet {

    private final ProductosDAO productosDAO = new ProductosDAO();

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
    // AGREGAR AL CARRITO
    // ---------------------------------------------------------
    private void agregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidadSolicitada = Integer.parseInt(request.getParameter("Cantidad_Stock"));

        boolean ok = productosDAO.agregarAlCarrito(idUsuario, idProducto, cantidadSolicitada);

        if (ok) {
            request.setAttribute("mensaje", "✅ Producto agregado correctamente al carrito.");
        } else {
            request.setAttribute("mensaje", "❌ Error al agregar el producto al carrito.");
        }

        listarProductos(request, response);
    }
}
