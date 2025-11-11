/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Modelo.Conexiones.ConexionEmail;
/**
 *
 * @author lagar
 */
public class ServicioGmail {
    private final ConexionEmail gmail = new ConexionEmail("luisrafaellagarda@gmail.com", "geuovtjluuyeeuhr");
    public ServicioGmail(){
    
    }
    
    public void enviarCorreoAsync(String destinatario, String asunto, String mensaje) {
        new Thread(() -> {
            try {
                gmail.enviarCorreo(destinatario, asunto, mensaje);
            } catch (Exception e) {
                System.out.println("Error: no se pudo enviar el correo a " + destinatario);
            }
        }).start();
    }
}
