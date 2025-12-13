<%-- 
    Document : PrincipalVentas
    Created on : Dec 12, 2025
    Author     : Tu nombre
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Entidades.VentaDetalleCompleta" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Ventas</title>
    <link rel="stylesheet" href="estilosCSS/estiloVentas.css">
</head>
<body>
    <div class="background-blur"></div>

    <!-- ENCABEZADO -->
    <div class="header">
        <div class="logo">
            <span>🛒 Gestión de Ventas</span>
        </div>
        <button class="btn-regresar"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=inicio'">
            ⬅ Regresar
        </button>
    </div>

    <!-- CONTENEDOR PRINCIPAL -->
    <div class="contenedor-flex">
        <!-- TABLA DE VENTAS -->
        <div class="contenedor-tabla">
            <h1 class="titulo-pagina">Lista de Ventas</h1>
            <div class="tabla-contenedor">
                <table class="tabla-ventas">
                    <thead>
                    <tr>
                        <th>ID Venta</th>
                        <th>Fecha</th>
                        <th>ID Cliente</th>
                        <th>Cliente</th>
                        <th>ID Producto</th>
                        <th>Producto</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Cantidad</th>
                        <th>Precio Unit.</th>
                        <th>Total Línea</th>
                        <th>Total Venta</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<VentaDetalleCompleta> listaVentas = 
                            (List<VentaDetalleCompleta>) request.getAttribute("listaVentas");
                        
                        if (listaVentas != null && !listaVentas.isEmpty()) {
                            for (VentaDetalleCompleta v : listaVentas) {
                    %>
                    <tr>
                        <td><%= v.getIdVenta() %></td>
                        <td><%= v.getFecha() %></td>
                        <td><%= v.getIdUsuario() %></td>
                        <td><%= v.getNombreCompleto() %></td>
                        <td><%= v.getIdProducto() %></td>
                        <td><%= v.getProducto() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getModelo() %></td>
                        <td><%= v.getCantidad() %></td>
                        <td>$<%= String.format("%.2f", v.getPrecioUnitario()) %></td>
                        <td>$<%= String.format("%.2f", v.getTotalLinea()) %></td>
                        <td>$<%= String.format("%.2f", v.getTotalVenta()) %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="12" class="mensaje-vacio">No hay ventas registradas.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- BOTONES CRUD A LA DERECHA -->
        <div class="contenedor-botones-der">
            <button class="btn-crud btn-editar">Editar</button>
            <button class="btn-crud btn-eliminar">Eliminar</button>
        </div>
    </div>
</body>
</html>