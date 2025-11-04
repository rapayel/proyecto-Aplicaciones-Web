/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Conexiones;

/**
 *
 * @author lagar
 */
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class ConexionEmail {

    private final String username;
    private final String password;
    private final Session session;

    public ConexionEmail(String username, String password) {
        this.username = username;
        this.password = password;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void enviarCorreo(String destinatario, String asunto, String mensajeTexto) {
        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(username));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(mensajeTexto);

            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente a " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
