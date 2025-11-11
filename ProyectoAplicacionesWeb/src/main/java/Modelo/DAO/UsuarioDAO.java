/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Usuarios;
import Servicios.ServicioIncriptador;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 
 * @author lagar
 */
public class UsuarioDAO {
    private final ConexionMySQL conexion = new ConexionMySQL();
        
    public boolean registrarUsuario(Usuarios usuario) {
        String sql = "INSERT INTO Usuarios (nombreCompleto, nombreUsuario, direccion, correo, contraseña) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conexion.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ServicioIncriptador aes = new ServicioIncriptador();

            ps.setString(1, aes.encriptado(usuario.getNombreCompleto()));
            ps.setString(2, aes.encriptado(usuario.getNombreUsuario()));
            ps.setString(3, aes.encriptado(usuario.getDireccion()));
            ps.setString(4, aes.encriptado(usuario.getCorreo()));
            ps.setString(5, aes.encriptado(usuario.getContraseña()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }   

    public Usuarios validarLogin(String nombreUsuario, String password) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE nombreUsuario = ?";

        try (Connection con = conexion.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ServicioIncriptador.encriptado(nombreUsuario));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombreUsuarioDesencriptado = ServicioIncriptador.desencriptar(rs.getString("nombreUsuario"));
                    String contraseñaDesencriptada = ServicioIncriptador.desencriptar(rs.getString("contraseña"));
                    System.out.println("Comparando:");
                    System.out.println("Ingresado usuario: " + nombreUsuario + " | BD: " + nombreUsuarioDesencriptado);
                    System.out.println("Ingresado pass: " + password + " | BD: " + contraseñaDesencriptada);
                    if (nombreUsuario.equals(nombreUsuarioDesencriptado) && password.equals(contraseñaDesencriptada)) {
                        return new Usuarios(
                            rs.getString("id"),
                            rs.getString("nombreCompleto"),
                            nombreUsuarioDesencriptado,
                            rs.getString("direccion"),
                            rs.getString("correo"),
                            contraseñaDesencriptada,
                            rs.getString("imagenPerfil")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar login: " + e.getMessage());
            throw e;
        }
        return null; 
    }


    
    public Usuarios obtenerUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT correo, contraseña FROM Usuarios WHERE nombreUsuario = ?";
        
        try (Connection con = conexion.conexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ServicioIncriptador aes = new ServicioIncriptador();
            String nombreUsuarioEncriptado = aes.encriptado(nombreUsuario); 
            ps.setString(1, nombreUsuarioEncriptado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String correoEncriptado = rs.getString("correo");
                    String contrasenaEncriptada = rs.getString("contraseña");
                    String correoDesencriptado = aes.desencriptar(correoEncriptado);
                    String contrasenaDesencriptada = aes.desencriptar(contrasenaEncriptada);
                    Usuarios usuario = new Usuarios(); 
                    usuario.setCorreo(correoDesencriptado); 
                    usuario.setContraseña(contrasenaDesencriptada);      
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al buscarUsuarioParaRecuperacion: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error de encriptación al buscarUsuarioParaRecuperacion: " + e.getMessage());
            throw new SQLException("Error de datos o seguridad durante la búsqueda de la cuenta.", e);
        }
        return null;
    }
}
