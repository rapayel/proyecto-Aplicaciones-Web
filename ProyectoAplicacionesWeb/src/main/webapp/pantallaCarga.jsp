<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Procesando Pago</title>
<link rel="stylesheet" href="estilosCSS/estilopantallaCarga.css">
</head>
<body>

<div class="loading-overlay" id="loading" style="display:none;">
    <div class="loading-box">
        <div class="spinner"></div>
        <div class="loading-text">Cargando...</div>
    </div>
</div>

<script>
document.getElementById("loading").style.display = "flex";
setTimeout(() => {
    window.location.href = "exitoso.jsp";
}, 3000);
</script>

</body>
</html>
