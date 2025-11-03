/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lagar
 */
@WebServlet(name = "ControladorTienda", urlPatterns = {"/ControladorTienda"})
public class ControladorTienda extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        
        if("validarLogin".equals(accion)){
            validarLogin(request, response);
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
    
    private void validarLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");

        ConexionMySQL conexionBD = new ConexionMySQL();
        Connection con = conexionBD.conexion();

        try {
            String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Crear objeto Usuario
                Usuario usuario = new Usuario(
                    rs.getString("id"),
                    rs.getString("nombreCompleto"),
                    rs.getString("nombreUsuario"),
                    rs.getString("direccion"),
                    rs.getString("correo"),
                    rs.getString("contraseña")
                );

                // Crear sesión y guardar al usuario
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", usuario);

                // Redirigir a página principal
                response.sendRedirect("Principal.jsp");
            } else {
                // Si no existe el usuario
                request.setAttribute("mensajeError", "Correo o contraseña incorrectos.");
                request.getRequestDispatcher("IniciarSesion.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error en la conexión o consulta: " + e.getMessage());
        } finally {
            conexionBD.desconectar();
        }
    }
}
