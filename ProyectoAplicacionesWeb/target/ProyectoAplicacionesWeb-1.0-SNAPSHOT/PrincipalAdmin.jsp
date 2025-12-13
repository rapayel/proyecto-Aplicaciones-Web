<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="Modelo.Entidades.ProductoTop" %>
<%@ page import="Modelo.Entidades.GananciaMes" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Administrador</title>
    <link rel="stylesheet" href="./estilosCSS/estiloPrincipalAdmin.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="background-blur"></div>
    <!-- HEADER -->
    <header class="header">
        <div class="logo">
            <span>Tienda Online Paneles Solares</span>
        </div>
        <div class="menu">
            <div class="dropdown">
                <button class="dropbtn">
                    Perfil
                    <%
                        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
                        if (nombreUsuario != null) {
                            out.print(" - " + nombreUsuario);
                        }
                    %>
                </button>
                <div class="dropdown-content">
                    <a href="?accion=logout">Cerrar sesión</a>
                </div>
            </div>
        </div>
    </header>
    <!-- LAYOUT -->
    <div class="contenedor-general">
        <aside class="sidebar">
            <form action="ControladorPrincipalAdmin" method="get">
                <input type="hidden" name="accion" value="inicio">
                <button class="btn-menu">Inicio</button>
            </form>
            <form action="ControladorPrincipalAdmin" method="get">
                <input type="hidden" name="accion" value="misVentas">
                <button class="btn-menu">Mis ventas</button>
            </form>
            <form action="ControladorPrincipalAdmin" method="get">
                <input type="hidden" name="accion" value="misProductos">
                <button class="btn-menu">Mis productos</button>
            </form>
            <form action="ControladorPrincipalAdmin" method="get">
                <input type="hidden" name="accion" value="inventario">
                <button class="btn-menu">Inventario</button>
            </form>
            <form action="ControladorPrincipalAdmin" method="get">
                <input type="hidden" name="accion" value="usuario">
                <button class="btn-menu">Usuarios</button>
            </form>
        </aside>
        <main class="panel-principal">
            <h1 class="titulo">Página Principal</h1>
            <%
                ProductoTop top = (ProductoTop) request.getAttribute("productoTop");
                double totalVentasMes = (Double) request.getAttribute("totalVentasMes"); // Ahora double
                List<Integer> ventasPorMes = (List<Integer>) request.getAttribute("ventasPorMes");
                List<GananciaMes> gananciasUltimos6Meses = (List<GananciaMes>) request.getAttribute("gananciasUltimos6Meses");
            %>
            <div class="dashboard-grid">
                <div class="card-destacada">
                    <h3>Producto más vendido</h3>
                    <% if (top != null) { %>
                        <img src="DB_Imagenes/<%= top.getImagen() %>" alt="<%= top.getNombreProducto() %>">
                        <p class="nombre"><%= top.getNombreProducto() %></p>
                        <p>Vendidos: <%= top.getUnidadesVendidas() %></p>
                        <p class="ingresos">Ingresos: $<%= top.getIngresoGenerado() %></p>
                    <% } else { %>
                        <p>No hay datos disponibles</p>
                    <% } %>
                </div>
                <div class="card-metrica">
                    <h3>Total ventas del mes</h3>
                    <p class="numero-grande">$<%= String.format("%.2f", totalVentasMes) %></p>
                </div>
                <div class="card-grafica">
                    <h3>Ventas por mes</h3>
                    <canvas id="graficaVentas"></canvas>
                </div>
                <div class="card-grafica">
                    <h3>Ganancias últimos 6 meses</h3>
                    <canvas id="graficaGanancias"></canvas>
                </div>
            </div>
        </main>
    </div>
    <script>
        // Datos de ventas por mes
        const ventasData = <%= ventasPorMes != null ? ventasPorMes.toString() : "[]" %>;

        // Datos de ganancias
        <%
            String gananciasJson = "[]";
            if (gananciasUltimos6Meses != null && !gananciasUltimos6Meses.isEmpty()) {
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < gananciasUltimos6Meses.size(); i++) {
                    sb.append(gananciasUltimos6Meses.get(i).getGanancia());
                    if (i < gananciasUltimos6Meses.size() - 1) sb.append(",");
                }
                sb.append("]");
                gananciasJson = sb.toString();
            }
        %>
        const gananciasData = <%= gananciasJson %>;

        // Etiquetas meses
        const meses = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
        const hoy = new Date();
        const labelsGanancias = [];
        for (let i = 5; i >= 0; i--) {
            let d = new Date(hoy.getFullYear(), hoy.getMonth() - i, 1);
            labelsGanancias.push(meses[d.getMonth()]);
        }

        // Gráfica Ventas
        new Chart(document.getElementById('graficaVentas'), {
            type: 'bar',
            data: {
                labels: ['Mes 1', 'Mes 2', 'Mes 3', 'Mes 4'],
                datasets: [{
                    label: 'Ventas',
                    data: ventasData,
                    backgroundColor: '#e50914'
                }]
            },
            options: {
                responsive: true,
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true } }
            }
        });

        // Gráfica Ganancias
        new Chart(document.getElementById('graficaGanancias'), {
            type: 'bar',
            data: {
                labels: labelsGanancias,
                datasets: [{
                    label: 'Ganancia',
                    data: gananciasData,
                    backgroundColor: '#00c853'
                }]
            },
            options: {
                responsive: true,
                plugins: { legend: { display: false } },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return '$' + value.toLocaleString();
                            }
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>