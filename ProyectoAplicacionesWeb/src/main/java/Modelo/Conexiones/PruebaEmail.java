/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Modelo.Conexiones;

/**
 *
 * @author lagar
 */
public class PruebaEmail {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.print("ayuda");
        String usuario = "luisrafaellagarda@gmail.com";
        String contrasena = "geuovtjluuyeeuhr";

        ConexionEmail gmail = new ConexionEmail (usuario, contrasena);
        gmail.enviarCorreo("luisrafaellagarda@gmail.com", "Prueba desde Java", "¡Hola! Este correo fue enviado desde Java.");
    }
    
}
