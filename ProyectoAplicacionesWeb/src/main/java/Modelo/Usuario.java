/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author lagar
 */
public class Usuario {
    private String id;
    private String nombreCompleto;
    private String nombreUsuario;
    private String direccion;
    private String correo;
    private String dontraseña;

    public Usuario() {
    }
    
    public Usuario(String nombreCompleto, String nombreUsuario, String direccion, String correo, String dontraseña) {
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.correo = correo;
        this.dontraseña = dontraseña;
    }

    public Usuario(String id, String nombreCompleto, String nombreUsuario, String direccion, String correo, String dontraseña) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.correo = correo;
        this.dontraseña = dontraseña;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDontraseña() {
        return dontraseña;
    }

    public void setDontraseña(String dontraseña) {
        this.dontraseña = dontraseña;
    }
    
    
    
    
}
