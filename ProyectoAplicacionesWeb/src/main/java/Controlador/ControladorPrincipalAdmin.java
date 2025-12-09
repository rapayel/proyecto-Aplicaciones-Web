package Controlador;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *s
 * @author Arell
 */
@WebServlet(name = "ControladorPrincipalAdmin", urlPatterns = {"/ControladorPrincipalAdmin"})
public class ControladorPrincipalAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "inicio";  // Valor por defecto
        }

        switch (accion) {

            case "inicio":
                request.getRequestDispatcher("PrincipalAdmin.jsp").forward(request, response);
                break;

            case "inventario":
                ProductosDAO daoInv = new ProductosDAO();
                List<Productos> listaInv = daoInv.listarInventario();

                request.setAttribute("listaProductos", listaInv);
                request.getRequestDispatcher("PrincipalInventario.jsp").forward(request, response);
                break;



            case "misProductos":

                // Cargar lista de productos desde DAO
                ProductosDAO dao = new ProductosDAO();
                List<Productos> lista = dao.listarProductos(); // tu método listar()

                request.setAttribute("listaProductos", lista);

                request.getRequestDispatcher("PrincipalProductos.jsp").forward(request, response);
                break;

            case "misVentas":
                request.getRequestDispatcher("Principal.jsp").forward(request, response);
                break;

            case "usuario":

                UsuarioDAO daoU = new UsuarioDAO();
                List<Usuarios> listaUsuarios = daoU.listarUsuarios(); // tu método listar()

                request.setAttribute("listaUsuarios", listaUsuarios);

                request.getRequestDispatcher("PrincipalUsuarios.jsp").forward(request, response);
                break;

            default:
                request.getRequestDispatcher("Principal.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
