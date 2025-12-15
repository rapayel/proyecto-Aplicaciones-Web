<%-- 
    Document   : PrincipalProductos
    Created on : Dec 7, 2025, 8:05:46 PM
    Author     : Arell
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Entidades.Productos" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Gestión de Productos</title>
    <link rel="stylesheet" href="estilosCSS/estiloPrincipalProductos.css">
</head>

<body>

    <div class="background-blur"></div>

    <div class="header">
        <div class="logo">
            <span>📦 Catálogo de Productos</span>
        </div>

        <button class="btn-regresar"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=inicio'">
            ⬅ Regresar
        </button>
    </div>

    <div class="contenedor-flex">

        <div class="contenedor-tabla">
            <h1 class="titulo-pagina">Gestión de Productos</h1>

            <div class="tabla-contenedor">
                <table class="tabla-productos">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Producto</th>
                            <th>Marca</th>
                            <th>Modelo</th>
                            <th>Descripción</th>
                            <th>Precio Compra</th>
                            <th>Precio Venta</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                            List<Productos> lista = (List<Productos>) request.getAttribute("listaProductos");

                            if (lista != null && !lista.isEmpty()) {
                                for (Productos p : lista) {
                        %>
                        <tr onclick="seleccionarFila(this)" 
                            style="cursor: pointer;"
                            data-id="<%= p.getId()%>"
                            data-producto="<%= p.getProducto()%>"
                            data-marca="<%= p.getMarca()%>"
                            data-modelo="<%= p.getModelo()%>"
                            data-descripcion="<%= p.getDescripcion()%>"
                            data-pcompra="<%= p.getPrecio_compra()%>"
                            data-pventa="<%= p.getPrecio_venta()%>">

                            <td><%= p.getId()%></td>
                            <td><%= p.getProducto()%></td>
                            <td><%= p.getMarca() %></td>
                            <td><%= p.getModelo() %></td>
                            <td><%= p.getDescripcion() %></td>
                            <td><%= p.getPrecio_compra()%></td>
                            <td><%= p.getPrecio_venta()%></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="7" class="mensaje-vacio">No hay productos registrados.</td>
                        </tr>
                        <% } %>
                    </tbody>

                </table>
            </div>
        </div>

        <div class="contenedor-botones-der">
            <button class="btn-crud btn-agregar" onclick="abrirAgregar()">Agregar</button>
            <button class="btn-crud btn-editar" onclick="abrirEditar()">Editar</button>
            <button class="btn-crud btn-eliminar" onclick="eliminarProducto()">Eliminar</button>
        </div>

    </div>

    <div id="modalProducto" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.7); z-index:999;">
        
        <div style="background:white; width:450px; margin: 50px auto; padding:20px; border-radius:8px; position:relative; max-height: 90vh; overflow-y: auto;">
            <h2 id="tituloModal" style="color:black; text-align:center;">Nuevo Producto</h2>

            <form action="ControladorProductos" method="POST">
                <input type="hidden" name="accion" value="guardar">
                <input type="hidden" id="txtId" name="id">

                <label style="color:black; display:block;">Nombre Producto:</label>
                <input type="text" id="txtProducto" name="txtProducto" required style="width:100%; margin-bottom:10px;">

                <label style="color:black; display:block;">Marca:</label>
                <input type="text" id="txtMarca" name="txtMarca" required style="width:100%; margin-bottom:10px;">

                <label style="color:black; display:block;">Modelo:</label>
                <input type="text" id="txtModelo" name="txtModelo" required style="width:100%; margin-bottom:10px;">

                <label style="color:black; display:block;">Descripción:</label>
                <textarea id="txtDescripcion" name="txtDescripcion" rows="3" style="width:100%; margin-bottom:10px;"></textarea>

                <label style="color:black; display:block;">Precio Compra:</label>
                <input type="number" step="0.01" id="txtPrecioCompra" name="txtPrecioCompra" required style="width:100%; margin-bottom:10px;">

                <label style="color:black; display:block;">Precio Venta:</label>
                <input type="number" step="0.01" id="txtPrecioVenta" name="txtPrecioVenta" required style="width:100%; margin-bottom:20px;">

                <div style="text-align:center;">
                    <button type="submit" style="background:green; color:white; padding:10px 20px; border:none; cursor:pointer;">Guardar</button>
                    <button type="button" onclick="cerrarModal()" style="background:red; color:white; padding:10px 20px; border:none; cursor:pointer;">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        let idSeleccionado = null;
        // Objeto para guardar temporalmente los datos de la fila clickeada
        window.datosFila = {};

        function seleccionarFila(fila) {
            // Limpiar selección previa
            let filas = document.querySelectorAll("tbody tr");
            filas.forEach(f => f.style.backgroundColor = "");

            // Marcar nueva selección
            fila.style.backgroundColor = "#cce5ff"; 
            fila.style.color = "black";

            // Obtener ID
            idSeleccionado = fila.getAttribute("data-id");

            // Guardar todos los datos en el objeto global
            window.datosFila = {
                id: fila.getAttribute("data-id"),
                producto: fila.getAttribute("data-producto"),
                marca: fila.getAttribute("data-marca"),
                modelo: fila.getAttribute("data-modelo"),
                descripcion: fila.getAttribute("data-descripcion"),
                pcompra: fila.getAttribute("data-pcompra"),
                pventa: fila.getAttribute("data-pventa")
            };
        }

        function abrirAgregar() {
            document.getElementById("modalProducto").style.display = "block";
            document.getElementById("tituloModal").innerText = "Nuevo Producto";
            
            // Limpiar campos
            document.getElementById("txtId").value = "";
            document.getElementById("txtProducto").value = "";
            document.getElementById("txtMarca").value = "";
            document.getElementById("txtModelo").value = "";
            document.getElementById("txtDescripcion").value = "";
            document.getElementById("txtPrecioCompra").value = "";
            document.getElementById("txtPrecioVenta").value = "";
        }

        function abrirEditar() {
            if (idSeleccionado == null) {
                alert("Por favor selecciona un producto de la tabla primero.");
                return;
            }
            document.getElementById("modalProducto").style.display = "block";
            document.getElementById("tituloModal").innerText = "Editar Producto";

            // Llenar campos con info de la fila
            document.getElementById("txtId").value = window.datosFila.id;
            document.getElementById("txtProducto").value = window.datosFila.producto;
            document.getElementById("txtMarca").value = window.datosFila.marca;
            document.getElementById("txtModelo").value = window.datosFila.modelo;
            document.getElementById("txtDescripcion").value = window.datosFila.descripcion;
            document.getElementById("txtPrecioCompra").value = window.datosFila.pcompra;
            document.getElementById("txtPrecioVenta").value = window.datosFila.pventa;
        }

        function eliminarProducto() {
            if (idSeleccionado == null) {
                alert("Selecciona un producto para eliminar.");
                return;
            }
            if (confirm("¿Seguro que quieres eliminar el producto ID " + idSeleccionado + "?")) {
                // Redirige al Servlet. Asegúrate de que tu Servlet escuche 'ControladorProducto'
                window.location.href = "ControladorProductos?accion=eliminar&id=" + idSeleccionado;
            }
        }

        function cerrarModal() {
            document.getElementById("modalProducto").style.display = "none";
        }
    </script>

</body>
</html>