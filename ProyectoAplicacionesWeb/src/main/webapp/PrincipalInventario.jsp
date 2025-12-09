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

<!-- CONTENEDOR PRINCIPAL: TABLA IZQ + COLUMNA DERECHA -->
<div class="contenedor-flex">

    <!-- TABLA DE INVENTARIO -->
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
                    List<Productos> lista = (List<Productos>) request.getAttribute("listaProductos");

                    if (lista != null && !lista.isEmpty()) {

                        for (Productos p : lista) {
                %>

                <tr>
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
                    <td colspan="4" class="mensaje-vacio">No hay productos en inventario.</td>
                </tr>
                <% } %>
                </tbody>

            </table>
        </div>

    </div> <!-- FIN TABLA -->

    <!-- COLUMNA DERECHA -->
    <div class="contenedor-lateral">

        <!-- CAMPO CANTIDAD ARRIBA -->
        <div class="cantidad-box">
            <label>Cantidad:</label>
            <input type="number" min="1" class="input-cantidad">
        </div>

        <!-- BOTONES CRUD -->
        <button class="btn-crud btn-comprar">Comprar</button>
        <button class="btn-crud btn-modificar">Modificar</button>
        <button class="btn-crud btn-eliminar">Eliminar</button>

    </div>

</div> <!-- FIN contenedor-flex -->

</body>
</html>
