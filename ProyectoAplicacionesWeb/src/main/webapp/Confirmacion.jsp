<%-- 
    Document   : CONFIRMACION
    Created on : Dec 9, 2025, 12:08:10 AM
    Author     : Arell
--%>


<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<% 
    String numPedido = (String) request.getAttribute("numPedido");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Confirmación | Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloCarrito.css">
</head>
<body>
    <header class="header">
        <div class="logo">
            ☀️ <span>Tienda Solar</span>
        </div>
    </header>

    <main class="contenedor-confirmacion">
        <h1>🎉 ¡Pedido Confirmado! 🎉</h1>
        <p>Gracias por tu compra. Tu pedido ha sido procesado correctamente.</p>
        
        <div class="numero-pedido">
            <h2>Número de Pedido: <%= numPedido %></h2>
        </div>
        
        <p>Hemos enviado una confirmación con los detalles a tu correo electrónico.</p>
        
        <a href="ControladorPrincipal?accion=listar" class="btn-volver">Volver a la tienda</a>
    </main>
</body>
</html>