/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.DAO.ProductosDAO;
import Modelo.Entidades.Productos;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ControladorProductos", urlPatterns = {"/ControladorProductos"})
public class ControladorProductos extends HttpServlet {

    ProductosDAO dao = new ProductosDAO();
    Productos producto = new Productos();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "Listar";
        }

        switch (accion) {
            case "Listar":
                request.setAttribute("productos", dao.listar());
                request.getRequestDispatcher("productos.jsp").forward(request, response);
                break;
            case "Agregar":
                request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
                break;

            case "Guardar":
                producto.setProducto(request.getParameter("producto"));
                producto.setMarca(request.getParameter("marca"));
                producto.setModelo(request.getParameter("modelo"));
                producto.setDescripcion(request.getParameter("descripcion"));
                producto.setPrecio_compra(Double.parseDouble(request.getParameter("precioCompra")));
                producto.setPrecio_venta(Double.parseDouble(request.getParameter("precioVenta")));
                producto.setCantidad_Stock(Integer.parseInt(request.getParameter("cantidadStock")));

                dao.agregarProducto(producto);
                response.sendRedirect("ControladorProductos?accion=Listar");
                break;

            case "Editar":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("producto", dao.buscarProducto(idEdit));
                request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
                break;

            case "Actualizar":
                producto.setId(Integer.parseInt(request.getParameter("id")));
                producto.setProducto(request.getParameter("producto"));
                producto.setMarca(request.getParameter("marca"));
                producto.setModelo(request.getParameter("modelo"));
                producto.setDescripcion(request.getParameter("descripcion"));
                producto.setPrecio_compra(Double.parseDouble(request.getParameter("precioCompra")));
                producto.setPrecio_venta(Double.parseDouble(request.getParameter("precioVenta")));
                producto.setCantidad_Stock(Integer.parseInt(request.getParameter("cantidadStock")));

                dao.actualizarProducto(producto);
                response.sendRedirect("ControladorProductos?accion=Listar");
                break;
            // Cambia "Eliminar" por "eliminar"
            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminarProducto(idEliminar);
                response.sendRedirect("ControladorProductos?accion=Listar"); 
                break;

            default:
                response.sendRedirect("ControladorProductos?accion=Listar");
        }
    }

    // Usamos doPost para GUARDAR y EDITAR (vienen del formulario)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion != null && accion.equalsIgnoreCase("guardar")) {
            try {
                // 1. Recibir datos del formulario (nombres coinciden con el name="" del JSP)
                String idStr = request.getParameter("id"); // Input hidden
                String nombre = request.getParameter("txtProducto");
                String marca = request.getParameter("txtMarca");
                String modelo = request.getParameter("txtModelo");
                String descripcion = request.getParameter("txtDescripcion");

                // Manejo de números (con validación básica para evitar errores si vienen vacíos)
                double pCompra = 0;
                double pVenta = 0;

                if (request.getParameter("txtPrecioCompra") != null && !request.getParameter("txtPrecioCompra").isEmpty()) {
                    pCompra = Double.parseDouble(request.getParameter("txtPrecioCompra"));
                }
                if (request.getParameter("txtPrecioVenta") != null && !request.getParameter("txtPrecioVenta").isEmpty()) {
                    pVenta = Double.parseDouble(request.getParameter("txtPrecioVenta"));
                }

                // 2. Llenar el objeto
                Productos p = new Productos();
                p.setProducto(nombre);
                p.setMarca(marca);
                p.setModelo(modelo);
                p.setDescripcion(descripcion);
                p.setPrecio_compra(pCompra);
                p.setPrecio_venta(pVenta);
                // Si tienes stock en el formulario, agrégalo. Si no, ponle un default o quítalo
                // p.setCantidad_Stock(Integer.parseInt(request.getParameter("txtStock"))); 

                // 3. Lógica INTELIGENTE (Insertar vs Actualizar)
                if (idStr == null || idStr.isEmpty()) {
                    // Si NO hay ID, es un producto NUEVO
                    // Asegúrate que tu DAO tenga un método agregar que reciba objeto
                    dao.agregarProducto(p);
                } else {
                    // Si HAY ID, es una EDICIÓN
                    p.setId(Integer.parseInt(idStr));
                    dao.actualizarProducto(p);
                }

                // 4. Redirigir SIEMPRE al controlador principal (tu vista bonita)
                response.sendRedirect("ControladorPrincipalAdmin?accion=inicio"); // O ?accion=productos según tu lógica

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error en ControladorProducto POST: " + e.getMessage());
            }
        }
    }

    // Usamos doGet para ELIMINAR 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion != null && accion.equalsIgnoreCase("eliminar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminarProducto(id);

                // Redirigir al controlador principal
                response.sendRedirect("ControladorPrincipalAdmin?accion=inicio");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
