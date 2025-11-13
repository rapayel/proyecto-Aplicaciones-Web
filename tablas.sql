DROP DATABASE IF EXISTS panelSolar;
CREATE DATABASE panelSolar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE panelSolar;
ALTER DATABASE panelSolar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE TABLE Usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombreCompleto VARCHAR(75) NOT NULL,
    nombreUsuario VARCHAR(25) NOT NULL UNIQUE,     
    direccion VARCHAR(125) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,           
    contraseña VARCHAR(255) NOT NULL,              
    rol VARCHAR(15) NOT NULL DEFAULT 'cliente',   
    imagenPerfil VARCHAR(255) DEFAULT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Usuarios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
select * from Usuarios;

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `producto` varchar(45) DEFAULT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `descripcion` text,
  `precioCompra` decimal(10,2) DEFAULT NULL,
  `precioVenta` decimal(10,2) DEFAULT NULL,
  `Cantidad_Stock` int DEFAULT '0',
  PRIMARY KEY (`id`)
);
INSERT INTO `productos` VALUES (1,'Panel Solar Monocristalino 400W','Trina Solar.','TS-400M','Alta eficiencia y rendimiento incluso en sombra',150.00,260.00,20),(2,'Panel Solar Flexible 100W','Renogy','RNG-FX100','Ideal para caravanas y superficies curvas',85.00,145.00,20),(3,'Inversor Solar 3000W Onda Pura','Growatt','GROW-3000','Convierte la energía solar en corriente alterna',280.00,480.00,10),(4,'Inversor Híbrido 5000W','Huawei','SUN2000-5KTL','Compatible con baterías y red eléctrica',450.00,750.00,8),(5,'Batería Litio 12V 100Ah','Battle Born','BB-12100','Larga vida útil y carga rápida',450.00,700.00,12),(6,'Batería AGM 12V 200Ah','Trojan','TRO-200AGM','Batería sellada libre de mantenimiento',300.00,520.00,15),(7,'Controlador de Carga MPPT 40A','Victron','VIC-40MPPT','Optimiza la carga de baterías desde el panel',110.00,180.00,18),(8,'Controlador PWM 30A','EPEVER','EP-PWM30','Regulador económico para sistemas pequeños',35.00,60.00,25),(9,'Kit Solar Básico 200W','EcoKit','EK-200','Incluye panel, regulador y cableado',220.00,350.00,10),(10,'Kit Solar Portátil 100W','Allpowers','AP-K100','Ideal para camping y emergencias',120.00,200.00,14),(11,'Cable Solar 4mm Rojo - 10m','SolarCable','SC-4R10','Cable resistente a rayos UV y altas temperaturas',10.00,18.00,40),(12,'Conectores MC4 (par)','SolarTech','MC4-ST','Conectores estándar para paneles solares',2.00,5.00,100),(13,'Cable Solar 6mm Negro - 10m','SolarCable','SC-6N10','Aislamiento doble y alta conductividad',12.00,22.00,30),(14,'Soporte Inclinación Panel Techo Plano','SunMount','SM-TPF','Aluminio resistente para instalación fija',30.00,55.00,16),(15,'Estructura para 4 Paneles','PanelRack','PR-4P','Estructura ajustable para techo metálico',80.00,140.00,8),(16,'Fusible Solar 20A','SolarSafe','SS-F20','Protección contra sobrecorrientes',3.00,7.00,50),(17,'Caja de conexiones IP65','SolarBox','SB-IP65','Protección para conexiones exteriores',15.00,28.00,20),(18,'Interruptor DC 1000V 32A','PVSwitch','PVS-32DC','Desconexión segura de corriente continua',20.00,35.00,15),(19,'Medidor de Energía Solar Digital','Victron','VIC-METER','Monitorea consumo y generación solar',45.00,80.00,10),(20,'Sensor de Temperatura para Batería','BatterySense','BS-TEMP','Optimiza la carga según la temperatura',10.00,18.00,25),(21,'Crimpadora para conectores solares','ToolTech','TT-CRIMP','Herramienta para MC4 y similares',25.00,45.00,12),(22,'Pelacables Solar Profesional','WirePro','WP-STRIP','Para cables solares de 2.5 a 6 mm²',12.00,22.00,20),(23,'Lámpara Solar LED 100W','GreenLight','GL-100LED','Lámpara exterior con panel integrado',35.00,65.00,22),(24,'Foco Solar con Sensor de Movimiento','LumiSolar','LS-SENSOR','Activación automática al detectar movimiento',20.00,38.00,30),(25,'Bomba de Agua Solar 12V','AgroSolar','AS-WP12','Ideal para riego con energía solar',90.00,150.00,7),(27,'Panel Solar','Tech','AS-23','Panel Solar 1000W',500.00,1000.00,0);
ALTER TABLE `productos` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
select * from productos;



