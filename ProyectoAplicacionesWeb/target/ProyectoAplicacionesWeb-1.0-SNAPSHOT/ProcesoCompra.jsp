<%-- 
    Document   : PROCESOCOMPRA
    Created on : Dec 9, 2025, 12:09:01 AM
    Author     : Arell
--%>


<%@ page import="java.util.*, Modelo.Entidades.Productos" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<% 
    List<Productos> carrito = (List<Productos>) request.getAttribute("carrito");
    Double total = (Double) request.getAttribute("total");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Checkout | Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloCarrito.css"> 
    </head>
<body>
    <header class="header">
        <div class="logo">
            ☀️ <span>Tienda Solar - Checkout</span>
        </div>
        <a href="ControladorCarrito?accion=verCarrito" class="volver">← Volver al Carrito</a>
    </header>

    <main class="contenedor-checkout">
        <h1>Finalizar Compra</h1>

        <div class="seccion-resumen">
            <h2>Resumen del Pedido</h2>
            <ul>
                <% for (Productos p : carrito) { %>
                    <li><%= p.getProducto() %> (x<%= p.getCantidad_Stock() %>) - $<%= String.format("%.2f", p.getPrecio_venta() * p.getCantidad_Stock()) %></li>
                <% } %>
            </ul>
            <p class="total-resumen">Total a pagar: <strong>$<%= String.format("%.2f", total) %> USD</strong></p>
        </div>

        <form action="ControladorCarrito" method="post" class="form-checkout">
            <input type="hidden" name="accion" value="confirmarPedido">
            
            <h2>1. Dirección de Envío</h2>
            <label for="direccionEnvio">Dirección Completa:</label>
            <input type="text" id="direccionEnvio" name="direccionEnvio" required>

            <h2>2. Método de Pago</h2>
            <select id="metodoPago" name="metodoPago" required>
                <option value="">Seleccione un método</option>
                <option value="TARJETA">Tarjeta de Crédito/Débito</option>
                <option value="PAYPAL">PayPal</option>
                <option value="EFECTIVO">Efectivo contra Entrega</option>
            </select>

            <p class="aviso">Al presionar "Confirmar Pedido", se procesará la transacción y se generará la orden.</p>
            
            <button type="submit" class="btn-confirmar btn-finalizar">Confirmar Pedido</button>
        </form>
    </main>
</body>
</html>

