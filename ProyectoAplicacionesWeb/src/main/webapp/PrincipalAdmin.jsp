<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Administrador</title>
    <link rel="stylesheet" href="estilosCSS/estiloPrincipalAdmin.css">
</head>

<body>

<div class="background-blur"></div>

<div class="contenedor-general">

    <!-- PANEL LATERAL -->
    <aside class="sidebar">

        <button class="btn-menu"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=inicio'">
            Inicio
        </button>

        <button class="btn-menu"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=inventario'">
            Mis compras
        </button>

        <button class="btn-menu"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=misProductos'">
            Mis productos
        </button>

        <button class="btn-menu"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=misVentas'">
            Mis ventas
        </button>

        <button class="btn-menu"
                onclick="window.location.href='ControladorPrincipalAdmin?accion=usuario'">
            Usuario
        </button>

    </aside>

    <!-- CONTENEDOR PRINCIPAL -->
    <main class="panel-principal">
        <h2 class="titulo">Página Principal</h2>

        <div class="grid-cajas">

            <div class="caja"></div>
            <div class="caja"></div>
            <div class="caja"></div>

            <div class="caja"></div>
            <div class="caja"></div>
            <div class="caja"></div>

        </div>

    </main>

</div>

</body>
</html>
