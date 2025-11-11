/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private final String url = "jdbc:mysql://localhost:3306/panelSolar?useSSL=false&serverTimezone=UTC";
    private final String usuario = "root";
    private final String contraseña = "xrapayel";
    private final String driver = "com.mysql.cj.jdbc.Driver";

    public Connection conexion() {
        Connection cn = null;
        try {
            System.out.println("Intentando cargar driver: " + driver);
            Class.forName(driver);

            System.out.println("Intentando conectar a MySQL...");
            cn = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos panelSolar");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error SQL al conectar con la base de datos.");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }

        return cn;
    }
}