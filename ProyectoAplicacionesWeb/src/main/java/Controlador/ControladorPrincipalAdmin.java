package Controlador;

import Modelo.DAO.ProductosDAO;
import Modelo.DAO.UsuarioDAO;
import Modelo.DAO.VentasDAO;
import Modelo.Entidades.Productos;
import Modelo.Entidades.Usuarios;
import Modelo.Entidades.ProductoTop;
import Modelo.Entidades.VentaDetalleCompleta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorPrincipalAdmin", urlPatterns = {"/ControladorPrincipalAdmin"})
public class ControladorPrincipalAdmin extends HttpServlet {

    private final ProductosDAO productosDAO = new ProductosDAO();
    private final VentasDAO ventasDAO = new VentasDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // === LOGOUT: PRIMERO ===
        if ("logout".equals(accion)) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("InicioSesion.html");
            return;
        }

        // === PROTECCIÓN ===
        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        String rol = (String) session.getAttribute("rol");
        if (idUsuario == null || !"admin".equals(rol)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // === NOMBRE USUARIO ===
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        if (nombreUsuario == null) {
            Usuarios u = usuarioDAO.obtenerUsuarioPorId(idUsuario);
            nombreUsuario = (u != null) ? u.getNombreCompleto() : "Administrador";
            session.setAttribute("nombreUsuario", nombreUsuario);
        }
        request.setAttribute("nombreUsuario", nombreUsuario);

        // === ACCIONES ===
        if (accion == null || "inicio".equals(accion)) {
            mostrarDashboard(request, response);
        } else if ("misVentas".equals(accion)) {
            request.setAttribute("listaVentas", ventasDAO.obtenerTodasLasVentasConDetalle());
            request.getRequestDispatcher("PrincipalVentas.jsp").forward(request, response);
        } else if ("misProductos".equals(accion)) {
            request.setAttribute("listaProductos", productosDAO.listarProductos());
            request.getRequestDispatcher("PrincipalProductos.jsp").forward(request, response);
        } else if ("inventario".equals(accion)) {
            request.setAttribute("listaProductos", productosDAO.listarInventario());
            request.getRequestDispatcher("PrincipalInventario.jsp").forward(request, response);
        } else if ("usuario".equals(accion)) {
            request.setAttribute("listaUsuarios", usuarioDAO.listarUsuarios());
            request.getRequestDispatcher("PrincipalUsuarios.jsp").forward(request, response);
        } else if ("stock".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String tipo = request.getParameter("tipo");
            boolean resultado = false;
            switch (tipo) {
                case "sumar":
                    resultado = productosDAO.sumarStock(id, cantidad);
                    break;

                case "modificar":
                    resultado = productosDAO.modificarStock(id, cantidad);
                    break;

                case "eliminar":
                    resultado = productosDAO.eliminarStock(id);
                    break;
            }
            request.getSession().setAttribute("resultadoStock", resultado);
            response.sendRedirect("ControladorPrincipalAdmin?accion=inventario");
        } else {
            mostrarDashboard(request, response);
        } 
    }

    private void mostrarDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("productoTop", productosDAO.obtenerProductoMasVendido());
        request.setAttribute("ventasPorMes", ventasDAO.obtenerVentasPorMes());
        request.setAttribute("totalVentasMes", ventasDAO.obtenerTotalVentasMes()); // Ahora devuelve double con ingresos
        request.setAttribute("gananciasUltimos6Meses", ventasDAO.obtenerGananciasUltimos6Meses());
        request.getRequestDispatcher("PrincipalAdmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    
}