<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pago Exitoso</title>
<link rel="stylesheet" href="estilosCSS/estiloExito.css">
</head>
<body>

<div class="success-overlay" id="success" style="display:none;">
    <div class="success-box">
        <div class="success-icon">✔</div>
        <div class="success-message">Operación exitosa</div>
        <div class="success-subtext">Los datos fueron guardados correctamente.</div>
        <button class="success-btn" onclick="window.location.href='Principal.jsp'">Continuar</button>
    </div>
</div>

<script>
document.getElementById("success").style.display = "flex";
</script>

</body>
</html>

