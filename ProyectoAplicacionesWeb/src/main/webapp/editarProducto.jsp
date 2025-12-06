<% Modelo.Entidades.Productos p=(Modelo.Entidades.Productos)request.getAttribute("producto"); %>
<form method="post" action="ControladorProductos?accion=Actualizar">
<input type="hidden" name="id" value="<%=p.getId()%>">

Producto: <input name="producto" value="<%=p.getProducto()%>"><br>
Marca: <input name="marca" value="<%=p.getMarca()%>"><br>
Modelo: <input name="modelo" value="<%=p.getModelo()%>"><br>
Descripción: <input name="descripcion" value="<%=p.getDescripcion()%>"><br>
Precio Compra: <input name="precioCompra" value="<%=p.getPrecio_compra()%>"><br>
Precio Venta: <input name="precioVenta" value="<%=p.getPrecio_venta()%>"><br>
Stock: <input name="cantidadStock" value="<%=p.getCantidad_Stock()%>"><br>

<input type="submit" value="Actualizar">
</form>
