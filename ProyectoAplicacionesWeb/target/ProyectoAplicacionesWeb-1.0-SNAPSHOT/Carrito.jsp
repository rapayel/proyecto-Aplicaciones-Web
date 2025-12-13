

<%@ page import="java.util.*, Modelo.Entidades.Productos" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Carrito | Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloPrincipalJSP.css">
    <link rel="stylesheet" href="estilosCSS/estiloCarrito.css">
    <link rel="stylesheet" href="estilosCSS/estiloFooter.css">
</head>
<body>
   <jsp:include page="./componentes/header.jsp" />
    <main class="contenedor-carrito">

        <h1 class="titulo-carrito">🛒 Mi Carrito</h1>

        <%
            // Mostrar mensajes de confirmación/error
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
            // Obtener variables de Request
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
                            // Cálculo de subtotal por ítem
                            double subtotal = p.getPrecio_venta() * p.getCantidad_Stock();
                    %>
                    <tr>
                        <td><%= p.getProducto() %></td>
                        <td><%= p.getDescripcion() %></td>
                        <td>$<%= String.format("%.2f", p.getPrecio_venta()) %> USD</td>
                        
                        <%-- NUEVO: Columna para MODIFICAR CANTIDAD --%>
                        <td>
                            <form action="ControladorCarrito" method="get" class="form-cantidad">
                                <input type="hidden" name="accion" value="modificarCantidad">
                                <input type="hidden" name="idProducto" value="<%= p.getId() %>">
                                
                                <%-- Botón para Decrementar (Envía Cantidad Actual - 1). El DAO maneja si es <= 0 --%>
                                <button type="submit" 
                                        name="cantidad" 
                                        value="<%= p.getCantidad_Stock() - 1 %>" 
                                        class="btn-cantidad">-</button>
                                
                                <span class="cantidad-actual"><%= p.getCantidad_Stock() %></span>
                                
                                <%-- Botón para Incrementar --%>
                                <button type="submit" 
                                        name="cantidad" 
                                        value="<%= p.getCantidad_Stock() + 1 %>" 
                                        class="btn-cantidad">+</button>
                            </form>
                        </td>
                        
                        <td>$<%= String.format("%.2f", subtotal) %> USD</td>
                        
                        <%-- Columna para ELIMINAR --%>
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
                <h2>Total: $<%= String.format("%.2f", total) %> USD</h2>

                <%-- MODIFICADO: Formulario para iniciar el flujo de Checkout --%>
                <form action="ControladorCarrito" method="post">
                    <input type="hidden" name="accion" value="iniciarCheckout">
                    <button type="submit" class="btn-finalizar">Proceder al Pago y Envío</button>
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
    <div class="contenedor-botones">
      <a class="btn-volver" href="ControladorPrincipal?accion=listarProductos">
        < Volver a la tienda
      </a>
    </div>
        
<jsp:include page="./componentes/footer.jsp" />
</body>
</html>








