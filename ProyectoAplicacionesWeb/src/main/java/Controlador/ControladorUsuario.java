/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Conexiones.ConexionEmail;
import Modelo.Conexiones.ConexionMySQL;
import Modelo.DAO.UsuarioDAO;
import Modelo.Entidades.Usuarios;
import Servicios.ServicioGmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 *
 * @author lagar
 */
@WebServlet(name = "ControladorUsuario", urlPatterns = {"/ControladorUsuario"})
public class ControladorUsuario extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final  ServicioGmail  servicioGmail= new ServicioGmail();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorTienda</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorTienda at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

            switch (accion) {
                case "validarLogin":
                    validarLogin(request, response);
                    break;
                case "registrarUsuario":
                    registrarUsuario(request, response);
                    break;
                case "recuperarCuenta":
                    recuperarCuenta(request, response);
                    break;
                default:
                    response.sendRedirect("errorPagina.html");
            }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String nombreCompleto = request.getParameter("txtNombre");
        String nombreUsuario = request.getParameter("txtUsuario");
        String direccion = request.getParameter("txtDireccion");
        String correo = request.getParameter("txtEmail");
        String contraseña = request.getParameter("txtPassword");

        Usuarios nuevo = new Usuarios(null, nombreCompleto, nombreUsuario, direccion, correo, contraseña, null);

        if (usuarioDAO.registrarUsuario(nuevo)) {
            servicioGmail.enviarCorreoAsync(
                    correo,
                    "Bienvenido a nuestra tienda online de paneles solares",
                    "Gracias por registrarse en nuestra página. ¡Esté atento a nuestras notificaciones!"
            );
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("registro.html?error=1");
        }
    }
    
    private void validarLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String nombreUsuario = request.getParameter("txtUsuario");
        String password = request.getParameter("txtPassword");

        try {
            Usuarios usuario = usuarioDAO.validarLogin(nombreUsuario, password);
            if (usuario != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", usuario);
                response.sendRedirect("index.html");
            } else {
                request.setAttribute("mensajeError", "Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("InicioSesion.html").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error al validar usuario", e);
        }
    }
    
    private void recuperarCuenta (HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String nombreUsuario = request.getParameter("txtUsuario");
        try {
            Usuarios usuario = usuarioDAO.obtenerUsuario(nombreUsuario);

            if (usuario != null) {
                String correo = usuario.getCorreo();
                String contrasena = usuario.getContraseña();
                servicioGmail.enviarCorreoAsync(
                    correo, 
                    "Recuperación de Contraseña", 
                    "Tu contraseña es: " + contrasena + ". Por favor, no la olvides."
                );
                response.sendRedirect("InicioSesion.html?recuperacion=success"); 
            } else {
                request.setAttribute("mensajeError", "No existe este usuario.");
                request.getRequestDispatcher("recuperar.html").forward(request, response);
            }
            
        } catch (SQLException e) {
            System.err.println("Error de BD al recuperar cuenta: " + e.getMessage());
            request.setAttribute("mensajeError", "Error interno al procesar la solicitud de recuperación.");
            request.getRequestDispatcher("recuperar.html").forward(request, response);

        } catch (ServletException | IOException e) {
            throw new ServletException("Error durante la recuperación de cuenta: " + e.getMessage(), e);
        }
    }
}
