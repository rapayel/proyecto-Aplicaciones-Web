<%-- 
    Document   : PrincipalInventario
    Created on : Dec 7, 2025, 8:46:59 PM
    Author     : Arell
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Entidades.Productos" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inventario de Productos</title>
    <link rel="stylesheet" href="estilosCSS/estiloInventario.css">
</head>

<body>

<div class="background-blur"></div>

<!-- ENCABEZADO -->
<div class="header">
    <div class="logo">
        <span>📦 Inventario de Productos</span>
    </div>

    <button class="btn-regresar"
            onclick="window.location.href='ControladorPrincipalAdmin?accion=inicio'">
        ⬅ Regresar
    </button>
</div>

<div class="contenedor-flex">

    <!-- TABLA -->
    <div class="contenedor-tabla">
        <h1 class="titulo-pagina">Gestión de Inventario</h1>

        <div class="tabla-contenedor">
            <table class="tabla-inventario">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Producto</th>
                    <th>Modelo</th>
                    <th>Stock</th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<Productos> lista =
                            (List<Productos>) request.getAttribute("listaProductos");

                    if (lista != null && !lista.isEmpty()) {
                        for (Productos p : lista) {
                %>
                <tr onclick="seleccionarProducto(this)"
                    data-id="<%= p.getId() %>"
                    data-producto="<%= p.getProducto() %>"
                    data-modelo="<%= p.getModelo() %>"
                    data-stock="<%= p.getCantidad_Stock() %>">

                    <td><%= p.getId() %></td>
                    <td><%= p.getProducto() %></td>
                    <td><%= p.getModelo() %></td>
                    <td><%= p.getCantidad_Stock() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="mensaje-vacio">
                        No hay productos en inventario.
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="contenedor-lateral contenedor-botones-der">

        <div class="cantidad-box">
            <label>Cantidad:</label>
            <input type="number" min="1" class="input-cantidad">
        </div>

        <button class="btn-crud btn-comprar"
                onclick="accionStock('sumar')">Comprar</button>

        <button class="btn-crud btn-modificar"
                onclick="accionStock('modificar')">Modificar</button>

        <button class="btn-crud btn-eliminar"
                onclick="accionStock('eliminar')">Eliminar</button>

        <div id="productoSeleccionado"
             style="display:none; margin-top:20px; background:rgba(0,0,0,0.5); padding:15px; border-radius:10px;">
            <h3>Producto seleccionado</h3>
            <p><strong>ID:</strong> <span id="selId"></span></p>
            <p><strong>Producto:</strong> <span id="selNombre"></span></p>
            <p><strong>Modelo:</strong> <span id="selModelo"></span></p>
            <p><strong>Stock actual:</strong> <span id="selStock"></span></p>
        </div>

    </div>

</div>

<script>
let productoIdSeleccionado = null;
function seleccionarProducto(fila) {

    productoIdSeleccionado = fila.dataset.id;

    document.getElementById("selId").textContent = fila.dataset.id;
    document.getElementById("selNombre").textContent = fila.dataset.producto;
    document.getElementById("selModelo").textContent = fila.dataset.modelo;
    document.getElementById("selStock").textContent = fila.dataset.stock;

    document.getElementById("productoSeleccionado").style.display = "block";
}


function accionStock(tipo) {

    const cantidadInput = document.querySelector(".input-cantidad");
    const cantidad = cantidadInput.value;

    if (!productoIdSeleccionado) {
        alert("⚠️ Selecciona un producto primero");
        return;
    }

    if (tipo !== 'eliminar' && (!cantidad || cantidad <= 0)) {
        alert("⚠️ Ingresa una cantidad válida");
        return;
    }

    window.location.href =
        "ControladorPrincipalAdmin?accion=stock"
        + "&tipo=" + tipo
        + "&id=" + productoIdSeleccionado
        + "&cantidad=" + (cantidad || 0);
}
</script>

</body>
</html>
