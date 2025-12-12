<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="Modelo.Entidades.Usuarios" %>
<%
    // Validar sesión
    if (session.getAttribute("idUsuario") == null) {
        response.sendRedirect("InicioSesion.html");
        return;
    }
    
    // Obtener el usuario actualizado desde el atributo (enviado por el Servlet)
    // Si es null (primera vez que entra), usaremos los datos de sesión básica o redirigimos a cargar
    Usuarios u = (Usuarios) request.getAttribute("usuario");
    if (u == null) {
        // Truco: Si no hay usuario cargado, recargamos pasando por el Servlet
        response.sendRedirect("ControladorUsuario?accion=verPerfil");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil | Tienda Solar</title>
    <link rel="stylesheet" href="estilosCSS/estiloUsuario.css"> <style>
        /* Pequeños ajustes para el perfil */
        .perfil-container {
            max-width: 600px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .header-perfil { text-align: center; margin-bottom: 20px; }
        .btn-volver { background-color: #555; display: inline-block; text-align: center; text-decoration: none; color: white; padding: 10px; border-radius: 5px; width: 100%; margin-top: 10px;}
    </style>
</head>
<body>
    <div class="background-blur"></div>

    <div class="perfil-container">
        <div class="header-perfil">
            <h1> Mi Perfil</h1>
            <p>Actualiza tu información personal</p>
        </div>

        <% 
            String msg = (String) request.getAttribute("mensaje");
            String err = (String) request.getAttribute("error");
            if (msg != null) { out.print("<p style='color:green; text-align:center;'> " + msg + "</p>"); }
            if (err != null) { out.print("<p style='color:red; text-align:center;'> " + err + "</p>"); }
        %>

        <form action="ControladorUsuario" method="POST">
            <input type="hidden" name="accion" value="actualizarPerfil">
            <input type="hidden" name="id" value="<%= u.getId() %>">

            <div class="form-group">
                <label>Nombre Completo</label>
                <input type="text" name="txtNombreCompleto" value="<%= u.getNombreCompleto() %>" required>
            </div>

            <div class="form-group">
                <label>Nombre de Usuario</label>
                <input type="text" name="txtUsuario" value="<%= u.getNombreUsuario() %>" readonly style="background-color: #e9ecef; cursor: not-allowed;">
                <small>El usuario no se puede cambiar</small>
            </div>

            <div class="form-group">
                <label>Correo Electrónico</label>
                <input type="email" name="txtCorreo" value="<%= u.getCorreo() %>" required>
            </div>

            <div class="form-group">
                <label>Dirección de Envío</label>
                <input type="text" name="txtDireccion" value="<%= u.getDireccion() %>" required>
            </div>

            <button type="submit" class="btn"> Guardar Cambios</button>
            <a href="ControladorPrincipal?accion=listar" class="btn-volver">← Volver a la Tienda</a>
        </form>
    </div>
</body>
</html>

