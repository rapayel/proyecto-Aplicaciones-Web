/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author Arell
 */
public class ConexionMySQL {
        /*Datos del Servidor MYSQL*/
    public String url ="jdbc:mysql://localhost:3306";
    public String nombreBD = "paneles";
    public String usuario = "root";
    public String contra = "root";
    public String driver = "com.mysql.jdbc.Driver";
    

    private final String URL = "jdbc:mysql://localhost:3306/" + nombreBD 
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";

    private Connection link = null;
   
    public Connection conectar() {
        try {
            Class.forName(driver);
            link = DriverManager.getConnection(url, usuario, contra);
        } catch (ClassNotFoundException e) {
            System.err.println("Error Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error Conexión: " + e.getMessage());
        }
        return link;
    }

    public Connection conexion() {
        // Simplemente llamamos al método nuevo. Así ambos hacen lo mismo.
        return this.conectar();
    }
    
    
    public void desconectar() {
        try {
            if (link != null && !link.isClosed()) {
                link.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }

    
    public boolean verificarConexion() {
        boolean estado = false;
        try {
            if (link == null || link.isClosed()) {
                conexion(); // intenta conectar si no hay una conexión activa
            }
            if (link != null && !link.isClosed()) {
                estado = true;
                JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos: " + nombreBD);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar la conexión: " + e.getMessage());
        }
        return estado;
    }

  
}