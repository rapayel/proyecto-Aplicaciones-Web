package Controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *@author deibi
 */
@WebServlet(name = "ControladorLogout", urlPatterns = {"/Logout"})
public class ControladorLogOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener la sesión actual
        HttpSession session = request.getSession(false); // false = no crear si no existe

        if (session != null) {
            // 2. Invalidar la sesión (borrar usuario, rol, carrito temporal)
            session.invalidate();
        }

        // 3. Redirigir al inicio de sesión o página principal
        response.sendRedirect("InicioSesion.html");
    }
}
