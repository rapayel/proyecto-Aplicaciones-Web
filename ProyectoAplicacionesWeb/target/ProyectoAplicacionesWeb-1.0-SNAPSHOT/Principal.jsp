<%@ page import="java.util.*, Modelo.Entidades.Productos" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloPrincipalJSP.css">
</head>
<body>
    <div class="background-blur"></div>

    <header class="header">
        <div class="logo">
            ☀️ <span>Tienda Solar</span>
        </div>

        <nav class="menu">
            <div class="dropdown">
                <button class="dropbtn">Perfil ▼</button>
                <div class="dropdown-content">
                    <a href="verPerfil.jsp">Ver perfil</a>
                    <a href="Logout">Cerrar sesión</a>
                </div>
            </div>

            <div class="carrito">
                <a href="ControladorCarrito?accion=verCarrito" class="carrito-link" title="Ver carrito">
                    🛒 <span id="contador">0</span>
                </a>
                <p class="nombre-usuario">
                    <% 
                        String usuario = (String) session.getAttribute("nombreUsuario");
                        if (usuario != null) out.print(usuario);
                        else out.print("Invitado");
                    %>
                </p>
            </div>
        </nav>
    </header>
   
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
                <img src="images/solar.png" alt="Icono solar" class="icono-solar"> Productos Disponibles
            </h2>
        </div>
        
        <div class="contenedor-productos">
        <%
            if (lista != null && !lista.isEmpty()) {
                for (Productos p : lista) {
        %>
            <div class="producto">
                <img src="images/panel_monocristalino_400w.png" alt="Imagen del producto"> 
                <h3><%= p.getProducto() %></h3>
                <p class="descripcion"><%= p.getDescripcion() %></p>
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
</body>
</html>
