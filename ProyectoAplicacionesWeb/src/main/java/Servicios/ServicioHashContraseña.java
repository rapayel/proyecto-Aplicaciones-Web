/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author lagar
 */
public class ServicioHashContraseña {
    public static String hashContraseña(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(contraseña.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static boolean verificarContraseña(String passwordPlano, String hashGuardado) {
        return hashContraseña(passwordPlano).equals(hashGuardado);
    }
}
