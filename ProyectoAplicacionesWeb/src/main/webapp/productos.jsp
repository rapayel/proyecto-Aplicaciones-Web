<%@page import="java.util.List"%>
<%@page import="Modelo.Entidades.Productos"%>

<html>
<head>
    <title>Productos</title>
    <link rel="stylesheet" type="text/css" href="./estilosCSS/estiloProducto.css">
</head>
<body>
    <h2>LISTA DE PRODUCTOS</h2>

    <a href="ControladorProductos?accion=Agregar">Nuevo Producto</a>
    <br><br>

    <table border="1">
    <tr>
        <th>ID</th><th>Producto</th><th>Marca</th><th>Modelo</th>
        <th>Compra</th><th>Venta</th><th>Stock</th><th>Acciones</th>
        </tr>

        <%
            List<Productos> lista=(List<Productos>)request.getAttribute("productos");
            for(Productos p : lista){
        %>
        <tr>
        <td><%=p.getId()%></td>
        <td><%=p.getProducto()%></td>
        <td><%=p.getMarca()%></td>
        <td><%=p.getModelo()%></td>
        <td><%=p.getPrecio_compra()%></td>
        <td><%=p.getPrecio_venta()%></td>
        <td><%=p.getCantidad_Stock()%></td>
        <td>
        <a href="ControladorProductos?accion=Editar&id=<%=p.getId()%>">Editar</a>
        <a href="ControladorProductos?accion=Eliminar&id=<%=p.getId()%>">Eliminar</a>
        </td>
        </tr>
        <% } %>
    </table>

</body>
</html>

