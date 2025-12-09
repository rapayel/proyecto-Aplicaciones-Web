<%@ page import="java.util.*, Modelo.Entidades.Productos" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Carrito | Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloCarrito.css">
</head>
<body>
    <header class="header">
        <div class="logo">
             <span>Tienda Solar</span>
        </div>

        <nav class="menu">
            <a href="ControladorPrincipal?accion=listar" class="volver">← Seguir comprando</a>
            <div class="dropdown">
                <button class="dropbtn">Perfil ▼</button>
                <div class="dropdown-content">
                    <a href="verPerfil.jsp">Ver perfil</a>
                    <a href="Logout">Cerrar sesión</a>
                </div>
            </div>
        </nav>
    </header>

    <main class="contenedor-carrito">

        <h1 class="titulo-carrito">🛒 Mi Carrito</h1>

        <% 
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) { 
        %>
            <div class="mensaje">
                <%= mensaje %>
            </div>
        <% 
            } 
        %>

        <%
            List<Productos> carrito = (List<Productos>) request.getAttribute("carrito");
            Double total = (Double) request.getAttribute("total");
            if (total == null) total = 0.0;

            if (carrito != null && !carrito.isEmpty()) { 
        %>
            <table class="tabla-carrito">
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Descripción</th>
                        <th>Precio Unitario</th>
                        <th>Cantidad</th>
                        <th>Subtotal</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Productos p : carrito) {
                            double subtotal = p.getPrecio_venta() * p.getCantidad_Stock();
                    %>
                    <tr>
                        <td><%= p.getProducto() %></td>
                        <td><%= p.getDescripcion() %></td>
                        <td>$<%= p.getPrecio_venta() %> USD</td>
                        <td><%= p.getCantidad_Stock() %></td>
                        <td>$<%= subtotal %> USD</td>
                        <td>
                            <form action="ControladorCarrito" method="get">
                                <input type="hidden" name="accion" value="eliminarProducto">
                                <input type="hidden" name="idProducto" value="<%= p.getId() %>">
                                <button type="submit" class="btn-eliminar">🗑️ Eliminar</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <div class="total">
                <h2>Total: $<%= total %> USD</h2>

                <form action="ControladorCarrito" method="post">
                    <input type="hidden" name="accion" value="finalizarCompra">
                    <button type="submit" class="btn-finalizar">Finalizar compra</button>
                </form>
            </div>

        <% 
            } else { 
        %>
            <p class="mensaje-vacio">Tu carrito está vacío 🛒</p>
        <% 
            } 
        %>
    </main>

    <footer class="footer">
        <p>© 2025 Tienda Solar — Energía limpia y sostenible ☀️</p>
    </footer>
</body>
</html>
