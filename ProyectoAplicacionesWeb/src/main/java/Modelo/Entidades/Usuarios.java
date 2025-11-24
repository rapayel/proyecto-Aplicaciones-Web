/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author lagar
 */

public class Usuarios {
    private String id;
    private String nombreCompleto;
    private String nombreUsuario;
    private String direccion;
    private String correo;
    private String contraseña; 
    private String rol;
    private String imagenPerfil; 

    public Usuarios() {
    }

    public Usuarios(String id,String nombreCompleto, String nombreUsuario, String direccion, String correo, String contraseña) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuarios(String id, String nombreCompleto, String nombreUsuario, String direccion, String correo, String contraseña, String imagenPerfil) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.correo = correo;
        this.contraseña = contraseña;
        this.imagenPerfil = imagenPerfil;
    }
    
    
    public Usuarios(String id, String nombreCompleto, String nombreUsuario, String direccion, String correo, String contraseña, String rol, String imagenPerfil) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.imagenPerfil = imagenPerfil;
    }

    public Usuarios(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}

