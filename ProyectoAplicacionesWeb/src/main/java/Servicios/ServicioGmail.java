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