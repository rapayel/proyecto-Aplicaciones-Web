/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

/**
 *
 * @author Arell
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos;

public class ProductosDAO {

    private final ConexionMySQL cn = new ConexionMySQL();

    public List<Productos> listarProductos() {

        List<Productos> lista = new ArrayList<>();

        String sql = "{CALL sp_listarProductos()}";

        try (Connection con = cn.conexion();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Productos p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_compra(rs.getDouble("precioCompra"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
                p.setCantidad_Stock(rs.getInt("Cantidad_Stock"));

                lista.add(p);
            }

        } catch (SQLException e) {
<<<<<<< HEAD
            System.out.println(" Error DAO listarProductos: " + e.getMessage());
=======
            System.out.println("Error DAO listarProductos: " + e.getMessage());
>>>>>>> 7698b4d09af05065f24591bf50fd13e78eaa3a37
        }

        return lista;
    }

    public boolean agregarAlCarrito(int idUsuario, int idProducto, int cantidad) {

        String sql = "{CALL sp_AgregarProductoCarrito(?,?,?)}";

        try (Connection con = cn.conexion();
             CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);

            stmt.execute();
            return true;

        } catch (SQLException e) {
<<<<<<< HEAD
            System.out.println(" Error DAO agregarAlCarrito: " + e.getMessage());
=======
            System.out.println("Error DAO agregarAlCarrito: " + e.getMessage());
>>>>>>> 7698b4d09af05065f24591bf50fd13e78eaa3a37
            return false;
        }
    }
    
    public boolean agregarProducto(Productos p) {
        String sql = "INSagregarProductoERT INTO productos (producto, marca, modelo, descripcion, precioCompra, precioVenta, Cantidad_Stock) "
                   + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProducto());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModelo());
            ps.setString(4, p.getDescripcion());
            ps.setDouble(5, p.getPrecio_compra());
            ps.setDouble(6, p.getPrecio_venta());
            ps.setInt(7, p.getCantidad_Stock());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error DAO agregarProducto: " + e.getMessage());
        }
        return false;
    }
    
    public Productos buscarProducto(int id) {

        Productos p = null;
        String sql = "SELECT * FROM productos WHERE id=?";

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_compra(rs.getDouble("precioCompra"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
                p.setCantidad_Stock(rs.getInt("Cantidad_Stock"));
            }

        } catch (SQLException e) {
            System.out.println("Error DAO buscarProducto: " + e.getMessage());
        }
        return p;
    }

    public boolean actualizarProducto(Productos p) {

        String sql = "UPDATE productos SET producto=?, marca=?, modelo=?, descripcion=?, "
                   + "precioCompra=?, precioVenta=?, Cantidad_Stock=? WHERE id=?";

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProducto());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModelo());
            ps.setString(4, p.getDescripcion());
            ps.setDouble(5, p.getPrecio_compra());
            ps.setDouble(6, p.getPrecio_venta());
            ps.setInt(7, p.getCantidad_Stock());
            ps.setInt(8, p.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error DAO actualizarProducto: " + e.getMessage());
        }
        return false;
    }
    
    public boolean eliminarProducto(int id) {

        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error DAO eliminarProducto: " + e.getMessage());
        }
        return false;
    }
    
    public List<Productos> listar() {

        List<Productos> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (Connection con = cn.conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Productos p = new Productos();
                p.setId(rs.getInt("id"));
                p.setProducto(rs.getString("producto"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio_compra(rs.getDouble("precioCompra"));
                p.setPrecio_venta(rs.getDouble("precioVenta"));
                p.setCantidad_Stock(rs.getInt("Cantidad_Stock"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error DAO listar: " + e.getMessage());
        }
        return lista;
    }
    public List<Productos> listarInventario() {

    List<Productos> lista = new ArrayList<>();

    String sql = "{CALL sp_listarProductosStock()}";

    try (Connection con = cn.conexion();
         CallableStatement cs = con.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {

        while (rs.next()) {

            Productos p = new Productos();

            p.setId(rs.getInt("ID"));
            p.setProducto(rs.getString("PRODUCTO"));
            p.setModelo(rs.getString("MODELO"));
            p.setCantidad_Stock(rs.getInt("STOCK"));

            lista.add(p);
        }

    } catch (SQLException e) {
        System.out.println("Error DAO listarInventario (SP): " + e.getMessage());
    }

    return lista;
}

}

