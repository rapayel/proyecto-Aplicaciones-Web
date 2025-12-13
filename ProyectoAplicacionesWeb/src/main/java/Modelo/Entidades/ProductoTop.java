/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Arell
 */

public class ProductoTop {
    private int productoId;
    private String nombreProducto;
    private String imagen;
    private int unidadesVendidas;
    private double ingresoGenerado;

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getUnidadesVendidas() { return unidadesVendidas; }
    public void setUnidadesVendidas(int unidadesVendidas) { this.unidadesVendidas = unidadesVendidas; }

    public double getIngresoGenerado() { return ingresoGenerado; }
    public void setIngresoGenerado(double ingresoGenerado) { this.ingresoGenerado = ingresoGenerado; }
}
