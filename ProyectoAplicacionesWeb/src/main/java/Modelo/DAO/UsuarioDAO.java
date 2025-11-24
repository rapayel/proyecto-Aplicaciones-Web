/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Usuarios;
import Servicios.ServicioGmail;
import java.sql.*;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * @author lagar
 */
public class UsuarioDAO {
    private final ConexionMySQL cn = new ConexionMySQL();
    private final  ServicioGmail  gmail= new ServicioGmail();
    private Connection conexion;

    public UsuarioDAO() {
        ConexionMySQL cn = new ConexionMySQL();
        this.conexion = cn.conexion();
    }


        
//       private void recuperarCuenta (HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//        String nombreUsuario = request.getParameter("txtUsuario");
//        Connection con = cn.conexion();
//        if (con == null) {
//            request.setAttribute("mensajeError", "Error al conectar con la base de datos.");
//            request.getRequestDispatcher("InicioSesion.html").forward(request, response);
//            return;
//        }
//        String sql = "SELECT * FROM Usuarios WHERE nombreUsuario = ?";
//        try (PreparedStatement ps = con.prepareStatement(sql)){
//            ps.setString(1, nombreUsuario);
//            try(ResultSet rs = ps.executeQuery()){
//                if(rs.next()){
//                    String correo = rs.getString("correo");
//                    String contra = rs.getString("contraseña");
//                    response.sendRedirect("InicioSesion.html");
//                    gmail.enviarCorreoAsync(correo, "recuperar contraseña", "la contraseña es: " + contra + " porfavor, que no se te olvide");
//                    cn.desconectar();
//                }else{
//                    request.setAttribute("mensajeError", "No existe este usuario");
//                    request.getRequestDispatcher("recuperar.html").forward(request, response);
//                    cn.desconectar();
//                }
//            }
//        } catch (SQLException e) {
//            throw new ServletException("Error al intentar recuperar contraseña: " + e.getMessage(), e);
//        } 
//    }
    
    // METODOS DE ENCRIPTACION
    // Método para encriptar la contraseña usando BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Método para verificar la contraseña (al hacer login)
    public boolean verifyPassword(String password, String storedHashedPassword) {
        return BCrypt.checkpw(password, storedHashedPassword);
    }
   //LOGIN
    // Método para verificar usuario y contraseña usando el procedimiento almacenado
    // Método para verificar el login
    public int Loggin(Usuarios usuario) {
        int userId = -1;

        try {
            Connection con = cn.conexion();
            CallableStatement stmt = con.prepareCall("{CALL SP_Loggin(?)}");
            stmt.setString(1, usuario.getNombreUsuario());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("CONTRASEÑA");

                // Verificar si la contraseña ingresada coincide con el hash almacenado
                if (BCrypt.checkpw(usuario.getContraseña(), hashedPassword)) {
                    userId = rs.getInt("ID"); // Retorna el ID del usuario
                    System.out.println("Loggin Exitoso");
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en login: " + e.getMessage());
        }

        return userId; // Retorna -1 si no se encontró el usuario o la contraseña no es válida
    }
    
public boolean crearUsuario(Usuarios usuario) {

    String sql = "{CALL sp_CrearUsuario(?, ?, ?, ?, ?, ?)}";

    try {
            CallableStatement stmt = conexion.prepareCall("{CALL sp_CrearUsuario(?, ?, ?, ?, ?, ?)}");

            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, usuario.getDireccion());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, hashPassword(usuario.getContraseña())); // Encriptación
            stmt.setString(6, "CLIENTE");
            stmt.execute();
    return true;    
    } catch (SQLException e) {
        System.out.println("Error DAO crearUsuario: " + e.getMessage());
        return false;
    }
    
}


    
}
