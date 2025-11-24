/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

/**
 *
 * @author Arell
 */

import Modelo.Conexiones.ConexionMySQL;
import Modelo.Entidades.Productos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    private final ConexionMySQL cn = new ConexionMySQL();

    // ---------------------------------------------------------
    // LISTAR PRODUCTOS
    // ---------------------------------------------------------
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
            System.out.println("❌ Error DAO listarProductos: " + e.getMessage());
        }

        return lista;
    }

    // ---------------------------------------------------------
    // AGREGAR PRODUCTO AL CARRITO
    // ---------------------------------------------------------
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
            System.out.println("❌ Error DAO agregarAlCarrito: " + e.getMessage());
            return false;
        }
    }
}

