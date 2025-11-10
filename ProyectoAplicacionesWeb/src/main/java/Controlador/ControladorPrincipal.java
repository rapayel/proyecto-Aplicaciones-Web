package Controlador;

import Modelo.Entidades.Productos;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ControladorPrincipal", urlPatterns = {"/ControladorPrincipal"})
public class ControladorPrincipal extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/panelsolar";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarProductos(request, response);
                break;
            default:
                listarProductos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("agregarCarrito".equals(accion)) {
            agregarCarrito(request, response);
        } else {
            listarProductos(request, response);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Productos> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM productos";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

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
                   System.out.println("Producto cargado: " + p.getProducto());
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("productos", lista);
        request.getRequestDispatcher("Principal.jsp").forward(request, response);
    }

    private void agregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidadSolicitada = Integer.parseInt(request.getParameter("Cantidad_Stock"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM productos WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idProducto);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int cantidadDisponible = rs.getInt("Cantidad_Stock");
                    if (cantidadSolicitada <= cantidadDisponible) {
                        Productos p = new Productos();
                        p.setId(rs.getInt("id"));
                        p.setProducto(rs.getString("producto"));
                        p.setMarca(rs.getString("marca"));
                        p.setModelo(rs.getString("modelo"));
                        p.setDescripcion(rs.getString("descripcion"));
                        p.setPrecio_compra(rs.getDouble("precioCompra"));
                        p.setPrecio_venta(rs.getDouble("precioVenta"));
                        p.setCantidad_Stock(cantidadSolicitada);

                        HttpSession sesion = request.getSession();
                        List<Productos> carrito = (List<Productos>) sesion.getAttribute("carrito");
                        if (carrito == null) carrito = new ArrayList<>();
                        carrito.add(p);
                        sesion.setAttribute("carrito", carrito);

                        request.setAttribute("mensaje", "Producto agregado correctamente.");
                    } else {
                        request.setAttribute("mensaje", "No hay suficiente cantidad disponible.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al agregar al carrito.");
        }

        listarProductos(request, response);
    }
}
