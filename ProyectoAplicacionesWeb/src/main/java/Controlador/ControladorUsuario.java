/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;


import Modelo.DAO.UsuarioDAO;
import Servicios.ServicioGmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Modelo.Entidades.Usuarios;

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
    
    String accion = request.getParameter("accion");
    if (accion == null) accion = "";

    if (accion.equals("verPerfil")) {
        // Cargar datos del usuario
        HttpSession session = request.getSession();
        if (session.getAttribute("idUsuario") != null) {
            int id = (int) session.getAttribute("idUsuario");
            Usuarios u = usuarioDAO.obtenerUsuarioPorId(id);
            request.setAttribute("usuario", u);
            request.getRequestDispatcher("verPerfil.jsp").forward(request, response);
        } else {
            response.sendRedirect("InicioSesion.html");
        }
    } else {
        // Por defecto
        processRequest(request, response);
    }
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
            case "registrarCliente":
    registrarCliente(request, response);
    break;
        case "actualizarPerfil":
        actualizarPerfil(request, response);
        break;
             default:
                response.sendRedirect("errorPagina.html");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
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
         // 2. Obtener el ROL del usuario desde el SP
        String rol = usuarioDAO.obtenerRolPorId(userId);
        // Inicio de sesión exitoso → crear sesión
        HttpSession session = request.getSession();
        session.setAttribute("idUsuario", userId);
        session.setAttribute("nombreUsuario", nombreUsuario);
        session.setAttribute("rol", rol);

        // 4. Redirección según ROL
        if ("admin".equalsIgnoreCase(rol)) {
            response.sendRedirect("PrincipalAdmin.jsp");
        } else {
            response.sendRedirect("ControladorPrincipal?accion=listar");
        }

    } 
    else {
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
private void actualizarPerfil(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    int id = Integer.parseInt(request.getParameter("id"));
    String nombreCompleto = request.getParameter("txtNombreCompleto");
    String usuarioStr = request.getParameter("txtUsuario"); // Solo lectura, pero lo recibimos
    String correo = request.getParameter("txtCorreo");
    String direccion = request.getParameter("txtDireccion");

    Usuarios u = new Usuarios();
    u.setId(id);
    u.setNombreCompleto(nombreCompleto);
    u.setNombreUsuario(usuarioStr);
    u.setCorreo(correo);
    u.setDireccion(direccion);

    boolean actualizado = usuarioDAO.actualizarUsuario(u);

    if (actualizado) {
        // Actualizar datos en sesión también por si acaso (opcional)
        request.setAttribute("mensaje", "Perfil actualizado correctamente");
    } else {
        request.setAttribute("error", "No se pudo actualizar el perfil");
    }
    
    // Recargar la vista con los datos nuevos
    request.setAttribute("usuario", u);
    request.getRequestDispatcher("verPerfil.jsp").forward(request, response);
}



}
