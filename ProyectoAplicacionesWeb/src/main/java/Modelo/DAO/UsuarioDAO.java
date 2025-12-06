/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Usuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author lagar
 */
public class UsuarioDAO {

    private final ConexionMySQL cn = new ConexionMySQL();

    // Método para hashear la contraseña
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    // Método para verificar la contraseña
    public boolean verifyPassword(String password, String storedHashedPassword) {
        return BCrypt.checkpw(password, storedHashedPassword);
    }

    // Método de login que devuelve el ID del usuario si es exitoso, -1 si falla
    public int Loggin(Usuarios usuario) {
        int userId = -1;
        try (Connection con = cn.conexion(); 
             CallableStatement stmt = con.prepareCall("{CALL SP_Loggin(?)}")) {

            stmt.setString(1, usuario.getNombreUsuario());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("CONTRASEÑA");

                    if (verifyPassword(usuario.getContraseña(), hashedPassword)) {
                        userId = rs.getInt("ID");
                        System.out.println("✅ Login Exitoso. ID: " + userId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(" Error en login: " + e.getMessage());
        }
        return userId;
    }
        
    // Obtener el rol del usuario por su ID
    public String obtenerRolPorId(int idUsuario) {
        String rol = null;
        try (Connection con = cn.conexion();
             CallableStatement stmt = con.prepareCall("{CALL sp_ObtenerRolPorId(?)}")) {
            
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rol = rs.getString("ROL");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo rol: " + e.getMessage());
        }
        return rol;
    }

    public boolean crearUsuario(Usuarios usuario) {
        try (Connection con = cn.conexion();
             CallableStatement stmt = con.prepareCall("{CALL sp_CrearUsuario(?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, usuario.getDireccion());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, hashPassword(usuario.getContraseña())); 
            stmt.setString(6, "CLIENTE"); // 
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error crearUsuario: " + e.getMessage());
            return false;
        }
    }

    public List<Usuarios> listarUsuarios() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario"; 

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
        // Usamos el SP que vi en tu base de datos: sp_ActualizarUsuario
        String sql = "{CALL sp_ActualizarUsuario(?, ?, ?, ?, ?)}";

        try (Connection con = cn.conexion();
             CallableStatement stmt = con.prepareCall(sql)) {

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

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error eliminarUsuario: " + e.getMessage());
            // Si el usuario tiene ventas, esto fallará por Foreign Key. 
            // Es mejor manejar ese error avisando "No se puede borrar usuario con historial".
            return false;
        }
    }
}