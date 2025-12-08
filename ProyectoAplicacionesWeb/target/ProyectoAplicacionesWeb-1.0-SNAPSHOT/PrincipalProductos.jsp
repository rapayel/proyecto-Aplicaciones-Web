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

<!-- ENCABEZADO -->
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

    <!-- TABLA -->
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

                <tr>
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

    <!-- BOTONES CRUD (DERECHA) -->
    <div class="contenedor-botones-der">
        <button class="btn-crud btn-agregar">Agregar</button>
        <button class="btn-crud btn-editar">Editar</button>
        <button class="btn-crud btn-eliminar">Eliminar</button>
    </div>

</div>

</body>
</html>
