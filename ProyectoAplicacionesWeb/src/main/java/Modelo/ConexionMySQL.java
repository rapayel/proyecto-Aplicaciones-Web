/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author lagar
 */
public class ConexionMySQL {
        /*Datos del Servidor MYSQL*/
    public String url ="jdbc:mysql://localhost:3306";
    public String nombreBD = "panelSolar";
    public String usuario = "root";
    public String contra = "xrapayel";
    public String driver = "com.mysql.jdbc.Driver";
    
    Connection conexion = null;
    
    // Metodo para establecer la conexion con la BD
    public Connection conexion(){
        // Establecemos el intento de la conexion
        try {
            // Cargar los driver de la base de datos en tiempo real o dinámico
            Class.forName(driver);
            // Establecer la conexion
            conexion =  DriverManager.getConnection(url+"/"+nombreBD, usuario, contra);            
            //Mostrar un mensaje en dado caso que la conexion sea correcta
            /*JOptionPane.showMessageDialog(null,"Conexión Exitosa");*/
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexión fallida: " + e);
        }
        return conexion;           
    }
    
    public void desconectar(){
        try {
            if(conexion!= null && !conexion.isClosed()){
                conexion.close();                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } 
    
    public boolean verificarConexion() {
        boolean estado = false;
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion(); // intenta conectar si no hay una conexión activa
            }
            if (conexion != null && !conexion.isClosed()) {
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