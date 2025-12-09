/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.io.PrintWriter;

import Modelo.DAO.UsuarioDAO;
import Modelo.Entidades.Usuarios;
import Servicios.ServicioGmail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author lagar
 */
@WebServlet(name = "ControladorUsuario", urlPatterns = {"/ControladorUsuario"})
public class ControladorUsuario extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ServicioGmail servicioGmail = new ServicioGmail();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "validarLogin":
                validarLogin(request, response);
                break;
            case "registrarCliente":
                registrarCliente(request, response);
                break;
            case "recuperarCuenta":
                recuperarCuenta(request, response);
                break;

            default:
                response.sendRedirect("errorPagina.html");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

//    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//        String nombreCompleto = request.getParameter("txtNombre");
//        String nombreUsuario = request.getParameter("txtUsuario");
//        String direccion = request.getParameter("txtDireccion");
//        String correo = request.getParameter("txtEmail");
//        String contraseña = request.getParameter("txtPassword");
//
//        Usuarios nuevo = new Usuarios(null, nombreCompleto, nombreUsuario, direccion, correo, contraseña, null);
//
//        if (usuarioDAO.registrarUsuario(nuevo)) {
//            servicioGmail.enviarCorreoAsync(
//                    correo,
//                    "Bienvenido a nuestra tienda online de paneles solares",
//                    "Gracias por registrarse en nuestra página. ¡Esté atento a nuestras notificaciones!"
//            );
//            response.sendRedirect("index.html");
//        } else {
//            response.sendRedirect("registro.html?error=1");
//        }
//    }
    /**
     * Método encargado de validar el inicio de sesión de un usuario
     */
    private void validarLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String nombreUsuario = request.getParameter("txtUsuario");
        String password = request.getParameter("txtPassword");

        // Crear el objeto Usuario y asignar valores
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(password);

        // Ejecutar el método Loggin para validar credenciales
        int userId = usuarioDAO.Loggin(usuario);

        if (userId > 0) {
            //  Obtener el ROL del usuario desde el SP
            String rol = usuarioDAO.obtenerRolPorId(userId);
            // Inicio de sesión exitoso → crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("idUsuario", userId);
            session.setAttribute("nombreUsuario", nombreUsuario);
            session.setAttribute("rol", rol);

            //  Redirección según ROL
            if ("admin".equalsIgnoreCase(rol)) {
                response.sendRedirect("PrincipalAdmin.jsp");
            } else {
                response.sendRedirect("ControladorPrincipal?accion=listar");
            }

        } else {
            // Usuario o contraseña incorrectos → regresar al login con mensaje
            request.setAttribute("mensajeError", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
//
//private void recuperarCuenta(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//
//    String nombreUsuario = request.getParameter("txtUsuario");
//
//    Usuarios usuario = usuarioDAO.buscarPorNombreUsuario(nombreUsuario);
//
//    if (usuario != null) {
//        servicioGmail.enviarCorreoAsync(
//                usuario.getCorreo(),
//                "Recuperación de Cuenta",
//                "Tu contraseña es: " + usuario.getContraseña()
//        );
//
//        response.sendRedirect("InicioSesion.jsp?recuperacion=success");
//
//    } else {
//        request.setAttribute("mensajeError", "No existe este usuario.");
//        request.getRequestDispatcher("recuperar.jsp").forward(request, response);
//    }
//}

    private void registrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreCompleto = request.getParameter("txtNombreCompleto");
        String nombreUsuario = request.getParameter("txtUsuario");
        String direccion = request.getParameter("txtDireccion");
        String correo = request.getParameter("txtCorreo");
        String contrasena = request.getParameter("txtPassword");

        Usuarios nuevo = new Usuarios();
        nuevo.setNombreCompleto(nombreCompleto);
        nuevo.setNombreUsuario(nombreUsuario);
        nuevo.setDireccion(direccion);
        nuevo.setCorreo(correo);
        nuevo.setContraseña(contrasena);
        nuevo.setRol("cliente");

        boolean guardado = usuarioDAO.crearUsuario(nuevo);

        if (guardado) {
            // SOLO REDIRECCIONAR A PRINCIPAL
            response.sendRedirect("InicioSesion.html");
        } else {
            request.setAttribute("mensajeError", "No se pudo registrar el usuario.");
            request.getRequestDispatcher("Registrar.html").forward(request, response);
        }
    }

    /**
     * Método encargado de gestionar la solicitud de recuperación de contraseña.
     * Envía la contraseña actual del usuario a su correo electrónico.
     */
    private void recuperarCuenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreUsuario = request.getParameter("txtUsuario");

        // Llama al DAO para buscar el usuario por nombre
        // Se asume que el UsuarioDAO tiene el método buscarPorNombreUsuario(String)
        Usuarios usuario = usuarioDAO.buscarPorNombreUsuario(nombreUsuario);

        if (usuario != null) {
            // Asunto del correo
            String asunto = "Recuperación de Cuenta - Tienda Solar";

            // Cuerpo del correo (Aquí se envía la contraseña hasheada, lo cual es solo para
            String mensaje = "Hola " + usuario.getNombreCompleto() + ",\n\n"
                    + "Se ha solicitado la recuperación de tu contraseña. "
                    + "Por motivos de seguridad, si esto no fue solicitado por ti, ignora este correo.\n\n"
                    + "Tu contraseña registrada es: " + usuario.getContraseña() + "\n\n"
                    + "Atentamente,\nEquipo de Tienda Solar.";

            // Envía el correo de forma asíncrona
            servicioGmail.enviarCorreoAsync(
                    usuario.getCorreo(),
                    asunto,
                    mensaje
            );

            // Redirigir al login con un mensaje de éxito
            response.sendRedirect("InicioSesion.html?recuperacion=success");

        } else {
            // Usuario no encontrado
            request.setAttribute("mensajeError", "No existe ningún usuario registrado con ese nombre.");
            // Redirige al formulario de recuperación (Recuperar.html) para mostrar el mensaje
            request.getRequestDispatcher("Recuperar.html").forward(request, response);
        }
    }

    

}
