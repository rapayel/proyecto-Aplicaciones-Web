<%@ page import="java.util.*, Modelo.Entidades.Productos" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloPrincipalJSP.css">
    <link rel="stylesheet" href="estilosCSS/estiloFooter.css">
</head>
<body>
     <jsp:include page="./componentes/header.jsp" />
    <div class="background-blur"></div>
   
    <section>
         <%
            List<Productos> lista = (List<Productos>) request.getAttribute("productos");
            if (lista == null) {
                out.println("<p style='color:red;'>⚠️ La lista de productos es NULL (el servlet no la envió)</p>");
            } else if (lista.isEmpty()) {
                out.println("<p style='color:orange;'>⚠️ La lista está vacía (consulta sin resultados)</p>");
            }
        %>
    </section>
   
    <main>         
        <div class="titulo-productos">
            <h2>
                <img src="images/disponible.png" alt="Icono solar" class="icono-solar"> Productos Disponibles
            </h2>
        </div>
        
        <div class="contenedor-productos">
        <%
            if (lista != null && !lista.isEmpty()) {
                for (Productos p : lista) {
        %>
            <div class="producto">
                <img src="DB_Imagenes/<%= p.getImagen() %>" alt="Imagen del producto">
                <h3><%= p.getProducto() %></h3>
                <a href="ControladorPrincipal?accion=verProducto&id=<%= p.getId() %>&t=<%= System.currentTimeMillis() %>" 
                   class="link-verDetalles">
                    Ver detalles
                </a>
                <p class="precio">$<%= p.getPrecio_venta() %> USD</p>
                <form action="ControladorPrincipal?accion=agregarCarrito" method="post">
                    <input type="hidden" name="accion" value="agregarCarrito">
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <input type="hidden" name="idUsuario" value="<%= session.getAttribute("idUsuario") %>">
                    <input type="number" name="Cantidad_Stock" value="1" min="1" max="<%= p.getCantidad_Stock() %>">
                    <button type="submit" class="btn-agregar">Agregar al carrito</button>
                </form>                 
            </div>
        <%
                }
            } else {
        %>
            <p class="mensaje-vacio">No hay productos disponibles.</p>
        <%
            }
        %>
        </div>
    </main>
    <jsp:include page="./componentes/footer.jsp" />
</body>
</html>
