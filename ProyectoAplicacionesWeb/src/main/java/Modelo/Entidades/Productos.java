/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Dórame
 */
public class Productos {
    private int id;
    private String producto;
    private String marca;
    private String modelo;
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    private int Cantidad_Stock;

    public Productos(int id,String producto, String marca, String modelo, String descripcion, double precio_compra, double precio_venta, int cantidad) {
        this.id= id;
        this.producto = producto;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.precioCompra = precio_compra;
        this.precioVenta = precio_venta;
        this.Cantidad_Stock = cantidad;
    }
    public Productos(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio_compra() {
        return precioCompra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precioCompra = precio_compra;
    }

    public double getPrecio_venta() {
        return precioVenta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precioVenta = precio_venta;
    }

    public int getCantidad_Stock() {
        return Cantidad_Stock;
    }

    public void setCantidad_Stock(int cantidad) {
        this.Cantidad_Stock = cantidad;
    }
    
}
