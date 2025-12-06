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
            case "Eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminarProducto(idEliminar);
                response.sendRedirect("ControladorProductos?accion=Listar");
                break;

            default:
                response.sendRedirect("ControladorProductos?accion=Listar");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
