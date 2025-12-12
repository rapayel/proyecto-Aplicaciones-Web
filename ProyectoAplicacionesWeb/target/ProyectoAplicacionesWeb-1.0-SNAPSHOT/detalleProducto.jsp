<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.Entidades.Productos" %>

<%
    Productos p = (Productos) request.getAttribute("producto");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalles del Producto</title>

    <link rel="stylesheet" href="estilosCSS/estiloDetalleProducto.css">
    <link rel="stylesheet" href="estilosCSS/estiloPrincipalJSP.css">
     <link rel="stylesheet" href="estilosCSS/estiloFooter.css">
</head>

<body>
 <jsp:include page="./componentes/header.jsp" />

<div class="background-blur"></div>

<div class="container-detalle">

    <h2 class="titulo-detalle">
         Detalles del Producto
    </h2>

    <% if (p != null) { %>

    <div class="producto-detalle">

      
        <img src="DB_Imagenes/<%= p.getImagen() %>" alt="Imagen del producto">
        <div class="info-detalle">
            <p><strong>Nombre:</strong> <%= p.getProducto() %></p>
            <p><strong>Marca:</strong> <%= p.getMarca() %></p>
            <p><strong>Modelo:</strong> <%= p.getModelo() %></p>
            <p><strong>Descripción:</strong> <%= p.getDescripcion() %></p>

            <p class="precio-detalle">
                <strong>Precio:</strong> $<%= p.getPrecio_venta() %> MX
            </p>

            <p><strong>Stock disponible:</strong> <%= p.getCantidad_Stock() %></p>

            
            <form action="ControladorPrincipal" method="post" class="form-carrito">
                <input type="hidden" name="accion" value="agregarCarrito">

                <input type="hidden" name="idUsuario" value="1">
          
                <input type="hidden" name="id" value="<%= p.getId() %>">

                <label style="margin-top:10px; display:block;">Cantidad:</label>
                <input 
                    type="number" 
                    name="Cantidad_Stock" 
                    value="1" 
                    min="1" 
                    max="<%= p.getCantidad_Stock() %>"
                    class="input-cantidad"
                >

                <button type="submit" class="btn-agregar">
                    🛒 Agregar al carrito
                </button>
            </form>

          <div class="contenedor-botones">
             <a class="btn-volver" href="ControladorPrincipal?accion=listarProductos">
                 < Volver a la tienda
            </a>
          </div>
        </div>

        </div>

    </div>

    <% } else { %>

        <p class="msg-error"> No se encontró el producto.</p>

    <% } %>

</div>
<jsp:include page="./componentes/footer.jsp" />
</body>
</html>
