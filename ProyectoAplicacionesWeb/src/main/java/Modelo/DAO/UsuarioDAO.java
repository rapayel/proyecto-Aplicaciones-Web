/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Usuarios;
import Servicios.ServicioGmail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Usuarios;

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

    // Obtener el rol del usuario por su ID
    public String obtenerRolPorId(int idUsuario) {
        String rol = null;
        try (Connection con = cn.conexion(); CallableStatement stmt = con.prepareCall("{CALL sp_ObtenerRolPorId(?)}")) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rol = rs.getString("ROL");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo rol: " + e.getMessage());
        }

        rs.close();
        stmt.close();
        con.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error obteniendo rol: " + e.getMessage());
    }

    // registro de usuarios normales (Clientes)
    public boolean crearUsuario(Usuarios usuario) {
        try (Connection con = cn.conexion(); CallableStatement stmt = con.prepareCall("{CALL sp_CrearUsuario(?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, usuario.getDireccion());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, hashPassword(usuario.getContraseña()));
            stmt.setString(6, "CLIENTE"); // Rol por defecto

            stmt.execute();
    return true;    
    } catch (SQLException e) {
        System.out.println("Error DAO crearUsuario: " + e.getMessage());
        return false;
    }   
}  

public List<Usuarios> listarUsuarios() {

    List<Usuarios> lista = new ArrayList<>();

    String sql = "{CALL sp_ObtenerListaUsuariosFinal()}";

    try (Connection con = cn.conexion();
         CallableStatement cs = con.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {

        while (rs.next()) {

            Usuarios u = new Usuarios();

            u.setId(rs.getInt("ID"));
            u.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
            u.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            u.setDireccion(rs.getString("DIRECCION"));
            u.setCorreo(rs.getString("CORREO"));
            u.setRol(rs.getString("ROL"));

            lista.add(u);
        }

    } catch (SQLException e) {
        System.out.println("Error DAO listarUsuarios: " + e.getMessage());
    }
    return lista;
}

    // MÉTODOS PARA EL CRUD DE ADMINISTRADOR
    //  LISTAR TODOS LOS USUARIOS
    public List<Usuarios> listarUsuarios() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario"; // Nombre de tabla en singular según el SQL 

        try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("ID"));
                u.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                u.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                u.setDireccion(rs.getString("DIRECCION"));
                u.setCorreo(rs.getString("CORREO"));
                u.setRol(rs.getString("ROL"));
                // No traemos la contraseña por seguridad
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error listarUsuarios: " + e.getMessage());
        }
        return lista;
    }

    //  OBTENER USUARIO POR ID (Para llenar el formulario de edición)
    public Usuarios obtenerUsuarioPorId(int id) {
        Usuarios u = null;
        String sql = "SELECT * FROM usuario WHERE ID = ?";

        try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuarios();
                    u.setId(rs.getInt("ID"));
                    u.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                    u.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                    u.setDireccion(rs.getString("DIRECCION"));
                    u.setCorreo(rs.getString("CORREO"));
                    u.setRol(rs.getString("ROL"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerUsuarioPorId: " + e.getMessage());
        }
        return u;
    }

    // ACTUALIZAR USUARIO (Desde el Admin)
    public boolean actualizarUsuario(Usuarios u) {
        String sql = "{CALL sp_ActualizarUsuario(?, ?, ?, ?, ?)}";

        try (Connection con = cn.conexion(); CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setInt(1, u.getId());
            stmt.setString(2, u.getNombreCompleto());
            stmt.setString(3, u.getNombreUsuario());
            stmt.setString(4, u.getDireccion());
            stmt.setString(5, u.getCorreo());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error actualizarUsuario: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR USUARIO
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE ID = ?";

        try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error eliminarUsuario: " + e.getMessage());
            // Si el usuario tiene ventas, esto fallará por Foreign Key. 
            return false;
        }
    }

    public Usuarios buscarPorNombreUsuario(String nombreUsuario) {
        Usuarios u = null;
        String sql = "SELECT * FROM usuario WHERE NOMBRE_USUARIO = ?";

        try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuarios();
                    // Mapeamos las columnas exactas de tu tabla 'usuario'
                    u.setId(rs.getInt("ID"));
                    u.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                    u.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                    u.setDireccion(rs.getString("DIRECCION"));
                    u.setCorreo(rs.getString("CORREO"));
                    u.setContraseña(rs.getString("CONTRASEÑA")); // Hash de la contraseña
                    u.setRol(rs.getString("ROL"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorNombreUsuario: " + e.getMessage());
        }
        return u;
    }
}
