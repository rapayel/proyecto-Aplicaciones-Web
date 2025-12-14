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
                    onclick="window.location.href = 'ControladorPrincipalAdmin?accion=inicio'">
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
                            <tr onclick="seleccionarFila(this)" 
                                data-id="<%= u.getId()%>" 
                                data-nombre="<%= u.getNombreCompleto()%>" 
                                data-usuario="<%= u.getNombreUsuario()%>"
                                data-correo="<%= u.getCorreo()%>" 
                                data-direccion="<%= u.getDireccion()%>" 
                                data-rol="<%= u.getRol()%>"
                                style="cursor: pointer;">

                                <td><%= u.getId()%></td>
                                <td><%= u.getNombreCompleto()%></td>
                                <td><%= u.getCorreo()%></td>
                                <td><%= u.getRol()%></td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="4" style="text-align:center;">No hay usuarios registrados</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>

                    </table>
                </div>
            </div>

            <!-- BOTONES CRUD A LA DERECHA -->
            <div class="contenedor-botones-der">
                <button class="btn-crud btn-agregar" onclick="abrirAgregar()">Agregar</button>

                <button class="btn-crud btn-editar" onclick="abrirEditar()">Editar</button>

                <button class="btn-crud btn-eliminar" onclick="eliminarUsuario()">Eliminar</button>
            </div>

        </div>

        <div id="modalUsuario" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.7); z-index:999;">

            <div style="background:white; width:400px; margin: 100px auto; padding:20px; border-radius:8px; position:relative;">
                <h2 id="tituloModal" style="color:black; text-align:center;">Nuevo Usuario</h2>

                <form action="ControladorUsuario" method="POST">
                    <input type="hidden" name="accion" value="guardar_admin">
                    <input type="hidden" id="txtId" name="id">

                    <label style="color:black; display:block;">Nombre Completo:</label>
                    <input type="text" id="txtNombre" name="txtNombreCompleto" required style="width:100%; margin-bottom:10px;">

                    <label style="color:black; display:block;">Usuario:</label>
                    <input type="text" id="txtUsuario" name="txtUsuario" required style="width:100%; margin-bottom:10px;">

                    <label style="color:black; display:block;">Correo:</label>
                    <input type="email" id="txtCorreo" name="txtCorreo" required style="width:100%; margin-bottom:10px;">

                    <label style="color:black; display:block;">Rol:</label>
                    <select id="txtRol" name="txtRol" style="width:100%; margin-bottom:10px;">
                        <option value="cliente">Cliente</option>
                        <option value="admin">Administrador</option>
                    </select>

                    <label style="color:black; display:block;">Dirección:</label>
                    <input type="text" id="txtDireccion" name="txtDireccion" style="width:100%; margin-bottom:10px;">

                    <label style="color:black; display:block;">Contraseña:</label>
                    <input type="password" name="txtPassword" placeholder="(Dejar vacío si no cambia)" style="width:100%; margin-bottom:20px;">

                    <div style="text-align:center;">
                        <button type="submit" style="background:green; color:white; padding:10px 20px; border:none; cursor:pointer;">Guardar</button>
                        <button type="button" onclick="cerrarModal()" style="background:red; color:white; padding:10px 20px; border:none; cursor:pointer;">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
        <script>
            let idSeleccionado = null;

            // 1. FUNCION PARA SELECCIONAR FILA
            function seleccionarFila(fila) {
                // Quitar selección a otros
                let filas = document.querySelectorAll("tbody tr");
                filas.forEach(f => f.style.backgroundColor = "");

                // Marcar la actual
                fila.style.backgroundColor = "#cce5ff"; // Un azul claro
                fila.style.color = "black"; // Asegurar que el texto se vea

                // Guardar datos en variables globales o leerlos directo
                idSeleccionado = fila.getAttribute("data-id");

                // Guardamos los datos de la fila para usarlos al editar
                window.datosFila = {
                    id: fila.getAttribute("data-id"),
                    nombre: fila.getAttribute("data-nombre"),
                    usuario: fila.getAttribute("data-usuario"),
                    correo: fila.getAttribute("data-correo"),
                    rol: fila.getAttribute("data-rol"),
                    direccion: fila.getAttribute("data-direccion")
                };
            }

            // 2. BOTÓN AGREGAR (Abre el modal vacío)
            // Tienes que poner onclick="abrirAgregar()" en tu botón verde HTML
            function abrirAgregar() {
                document.getElementById("modalUsuario").style.display = "block";
                document.getElementById("tituloModal").innerText = "Nuevo Usuario";
                document.getElementById("txtId").value = ""; // ID vacío = Nuevo
                document.getElementById("txtNombre").value = "";
                document.getElementById("txtUsuario").value = "";
                document.getElementById("txtCorreo").value = "";
                document.getElementById("txtDireccion").value = "";
            }

            // 3. BOTÓN EDITAR (Abre el modal con datos)
            // Tienes que poner onclick="abrirEditar()" en tu botón amarillo HTML
            function abrirEditar() {
                if (idSeleccionado == null) {
                    alert("Por favor selecciona un usuario de la tabla primero.");
                    return;
                }
                document.getElementById("modalUsuario").style.display = "block";
                document.getElementById("tituloModal").innerText = "Editar Usuario";

                // Llenar campos con los datos guardados
                document.getElementById("txtId").value = window.datosFila.id;
                document.getElementById("txtNombre").value = window.datosFila.nombre;
                document.getElementById("txtUsuario").value = window.datosFila.usuario;
                document.getElementById("txtCorreo").value = window.datosFila.correo;
                document.getElementById("txtRol").value = window.datosFila.rol;
                document.getElementById("txtDireccion").value = window.datosFila.direccion;
            }

            // 4. BOTÓN ELIMINAR
            // Tienes que poner onclick="eliminarUsuario()" en tu botón rojo HTML
            function eliminarUsuario() {
                if (idSeleccionado == null) {
                    alert("Selecciona un usuario para eliminar.");
                    return;
                }
                if (confirm("¿Seguro que quieres eliminar al usuario ID " + idSeleccionado + "?")) {
                    // Redirige al controlador para borrar
                    window.location.href = "ControladorUsuario?accion=eliminar&id=" + idSeleccionado;
                }
            }

            function cerrarModal() {
                document.getElementById("modalUsuario").style.display = "none";
            }
        </script>
    </body>
</html>