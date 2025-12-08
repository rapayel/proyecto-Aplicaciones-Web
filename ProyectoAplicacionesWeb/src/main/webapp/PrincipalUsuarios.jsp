<%-- 
    Document   : PrincipalUsuarios
    Created on : Dec 7, 2025, 8:24:53 PM
    Author     : Arell
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Entidades.Usuarios" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>

    <link rel="stylesheet" href="estilosCSS/estiloUsuarios.css">
</head>

<body>

<div class="background-blur"></div>

<!-- ENCABEZADO -->
<div class="header">
    <div class="logo">
        <span>👤 Gestión de Usuarios</span>
    </div>

    <button class="btn-regresar"
            onclick="window.location.href='ControladorPrincipalAdmin?accion=inicio'">
        ⬅ Regresar
    </button>
</div>

<!-- CONTENEDOR PRINCIPAL -->
<div class="contenedor-flex">

    <!-- TABLA DE USUARIOS -->
    <div class="contenedor-tabla">

        <h1 class="titulo-pagina">Lista de Usuarios</h1>

        <div class="tabla-contenedor">
            <table class="tabla-usuarios">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Rol</th>
                </tr>
                </thead>

                <tbody>

                <%
                    List<Usuarios> lista = (List<Usuarios>) request.getAttribute("listaUsuarios");

                    if (lista != null && !lista.isEmpty()) {

                        for (Usuarios u : lista) {
                %>

                <tr>
                    <td><%= u.getId()%></td>
                    <td><%= u.getNombreCompleto()%></td>
                    <td><%= u.getCorreo() %></td>
                    <td><%= u.getRol() %></td>
                </tr>

                <%
                        }

                    } else {
                %>

                <tr>
                    <td colspan="4" class="mensaje-vacio">No hay usuarios registrados.</td>
                </tr>

                <% } %>
                </tbody>

            </table>
        </div>
    </div>

    <!-- BOTONES CRUD A LA DERECHA -->
    <div class="contenedor-botones-der">
        <button class="btn-crud btn-agregar">Agregar</button>
        <button class="btn-crud btn-editar">Editar</button>
        <button class="btn-crud btn-eliminar">Eliminar</button>
    </div>

</div>

</body>
</html>
