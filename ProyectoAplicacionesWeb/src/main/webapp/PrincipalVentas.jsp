<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Entidades.VentaDetalleCompleta" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Ventas</title>
    <link rel="stylesheet" href="estilosCSS/estiloVentas.css"> 
</head>

<body>

<div class="background-blur"></div>

<div class="header">
    <div class="logo">
        <span>Gestión de Ventas</span>
    </div>

    <button class="btn-regresar"
            onclick="window.location.href='ControladorPrincipalAdmin?accion=inicio'">
        Regresar
    </button>
</div>

<div class="contenedor-flex">

    <div class="contenedor-tabla">
        <h1 class="titulo-pagina">Historial de Ventas</h1>
        
        <%
            String mensaje = (String) session.getAttribute("mensajeVenta");
            if (mensaje != null) {
                session.removeAttribute("mensajeVenta");
        %>
            <div style="background-color: #4CAF50; color: white; padding: 10px; border-radius: 5px; margin-bottom: 10px; text-align: center;">
                <%= mensaje %>
            </div>
        <% } %>

        <div class="tabla-contenedor">
            <table class="tabla-ventas">
                <thead>
                <tr>
                    <th>ID Venta</th>
                    <th>Fecha</th>
                    <th>Cliente</th>
                    <th>Producto</th>
                    <th>Modelo</th>
                    <th>Cant.</th>
                    <th>Total</th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<VentaDetalleCompleta> listaVentas = 
                        (List<VentaDetalleCompleta>) request.getAttribute("listaVentas");

                    if (listaVentas != null && !listaVentas.isEmpty()) {
                        for (VentaDetalleCompleta v : listaVentas) {
                %>
                <tr onclick="seleccionarVenta(this)"
                    style="cursor: pointer;"
                    data-id-venta="<%= v.getIdVenta() %>"
                    data-fecha="<%= v.getFecha() %>"
                    data-nombre="<%= v.getNombreCompleto() %>"
                    data-producto="<%= v.getProducto() %>"
                    data-modelo="<%= v.getModelo() %>"
                    data-cantidad="<%= v.getCantidad() %>"
                    data-total-venta="<%= String.format("%.2f", v.getTotalVenta()) %>">

                    <td><%= v.getIdVenta() %></td>
                    <td><%= v.getFecha() %></td>
                    <td><%= v.getNombreCompleto() %></td>
                    <td><%= v.getProducto() %></td>
                    <td><%= v.getModelo() %></td>
                    <td><%= v.getCantidad() %></td>
                    <td>$<%= String.format("%.2f", v.getTotalVenta()) %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" class="mensaje-vacio">
                        No hay ventas registradas.
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="contenedor-lateral contenedor-botones-der">

        <button class="btn-crud btn-eliminar" onclick="eliminarVenta()">
            Eliminar Venta
        </button>

        <div id="ventaSeleccionada"
             style="display:none; margin-top:20px; background:rgba(0,0,0,0.5); padding:15px; border-radius:10px; color: white;">
            
            <h3 style="border-bottom: 1px solid #ddd; padding-bottom: 5px;">Venta Seleccionada</h3>
            <p><strong>ID Venta:</strong> <span id="selIdVenta"></span></p>
            <p><strong>Cliente:</strong> <span id="selCliente"></span></p>
            <p><strong>Producto:</strong> <span id="selProducto"></span></p>
            <p><strong>Total:</strong> <span id="selTotal" style="font-weight: bold; color: #4CAF50;"></span></p>
        </div>

    </div>

</div>

<script>
// Variable global para guardar el ID
let ventaIdSeleccionada = null;

function seleccionarVenta(fila) {
    // 1. Guardar el ID de la venta seleccionada
    ventaIdSeleccionada = fila.dataset.idVenta;

    // 2. Llenar los datos visuales en el cuadro de la derecha
    document.getElementById("selIdVenta").textContent = fila.dataset.idVenta;
    document.getElementById("selCliente").textContent = fila.dataset.nombre;
    document.getElementById("selProducto").textContent = fila.dataset.producto;
    document.getElementById("selTotal").textContent = "$" + fila.dataset.totalVenta;

    // 3. Mostrar el cuadro de detalles
    document.getElementById("ventaSeleccionada").style.display = "block";
}

function eliminarVenta() {
    // 1. Validar que se haya seleccionado algo
    if (!ventaIdSeleccionada) {
        alert("Por favor, selecciona una fila de la tabla primero.");
        return;
    }

    // 2. Pedir confirmación
    let confirmacion = confirm("¿Estás seguro de que deseas eliminar la venta ID: " + ventaIdSeleccionada + "?\n\n(El stock será devuelto al inventario)");
    
    // 3. Si acepta, redirigir al controlador
    if (confirmacion) {
        window.location.href = 
            "ControladorPrincipalAdmin?accion=eliminarVenta&idVenta=" + ventaIdSeleccionada;
    }
}
</script>

</body>
</html>