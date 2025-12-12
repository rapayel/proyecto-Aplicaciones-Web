<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="Modelo.Entidades.Productos" %>

<header class="header">
    <div class="logo">
        <span>Tienda Online Paneles Solares</span>
    </div>

    <nav class="menu">
        <div class="dropdown">
            <button class="dropbtn">Perfil</button>
            <div class="dropdown-content">
                <a href="verPerfil.jsp">Ver perfil</a>
                <a href="Logout">Cerrar sesión</a>
            </div>
        </div>

        <div class="carrito">
            <a href="ControladorCarrito?accion=verCarrito" class="carrito-link" title="Ver carrito">
                <img src="images/carrito.png" alt="Carrito" class="icono-solar">
                <span id="contador">
                    <%
                        List<Productos> carritoHeader =
                            (List<Productos>) session.getAttribute("carrito");

                        int totalProductos = 0;

                        if (carritoHeader != null) {
                            for (Productos p : carritoHeader) {
                                totalProductos += p.getCantidad_Stock();
                            }
                        }

                        out.print(totalProductos);
                    %>
                </span>

            </a>

            <p class="nombre-usuario">
                <%
                    String usuario = (String) session.getAttribute("nombreUsuario");
                    if (usuario != null) out.print(usuario);
                    else out.print("Invitado");
                %>
            </p>
        </div>
    </nav>
</header>
