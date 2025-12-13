-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: paneles
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carrito`
--

DROP TABLE IF EXISTS `carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrito` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `carrito_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrito`
--

LOCK TABLES `carrito` WRITE;
/*!40000 ALTER TABLE `carrito` DISABLE KEYS */;
INSERT INTO `carrito` VALUES (7,1),(1,3),(5,5),(6,8);
/*!40000 ALTER TABLE `carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `id` int NOT NULL AUTO_INCREMENT,
  `producto_id` int NOT NULL,
  `admin_id` int NOT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `producto_id` (`producto_id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `compras_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `usuario` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (1,1,1,'2025-05-07 10:00:00',30,150.00),(2,2,1,'2025-05-07 11:00:00',20,85.00),(3,3,1,'2025-05-07 12:00:00',10,280.00),(4,4,1,'2025-05-07 13:00:00',8,450.00),(5,5,1,'2025-05-07 14:00:00',12,450.00),(6,6,1,'2025-05-07 15:00:00',15,300.00),(7,7,1,'2025-05-07 16:00:00',18,110.00),(8,8,1,'2025-05-07 17:00:00',25,35.00),(9,9,1,'2025-05-07 18:00:00',10,220.00),(10,10,1,'2025-05-07 19:00:00',14,120.00),(11,11,1,'2025-05-07 20:00:00',40,10.00),(12,12,1,'2025-05-07 21:00:00',100,2.00),(13,13,1,'2025-05-07 22:00:00',30,12.00),(14,14,1,'2025-05-07 23:00:00',16,30.00),(15,15,1,'2025-05-07 23:30:00',8,80.00),(16,16,1,'2025-05-08 00:00:00',50,3.00),(17,17,1,'2025-05-08 01:00:00',20,15.00),(18,18,1,'2025-05-08 02:00:00',15,20.00),(19,19,1,'2025-05-08 03:00:00',10,45.00),(20,20,1,'2025-05-08 04:00:00',25,10.00),(21,21,1,'2025-05-08 05:00:00',12,25.00),(22,22,1,'2025-05-08 06:00:00',20,12.00),(23,23,1,'2025-05-08 07:00:00',22,35.00),(24,24,1,'2025-05-08 08:00:00',30,20.00),(25,25,1,'2025-05-08 09:00:00',7,90.00),(27,27,1,'2025-05-07 16:45:39',15,7500.00),(28,2,1,'2025-05-07 17:00:59',20,1700.00);
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `venta_id` int NOT NULL,
  `producto_id` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `venta_id` (`venta_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`),
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES (1,1,1,3,260.00),(2,1,2,2,145.00),(3,2,3,1,480.00),(4,2,4,1,520.00),(5,3,5,1,200.00),(6,3,6,1,22.00),(7,4,7,1,350.00),(8,4,8,2,5.00),(9,5,9,1,55.00),(10,5,10,2,28.00),(11,6,11,2,22.00),(12,6,12,1,45.00),(13,7,13,1,150.00),(14,7,14,1,35.00),(15,8,15,1,45.00),(16,8,16,1,38.00),(17,9,17,1,7.00),(18,9,18,1,18.00),(19,10,19,1,180.00),(20,10,20,2,35.00),(21,11,21,1,22.00),(22,11,22,1,18.00),(23,12,1,2,260.00),(24,12,2,3,145.00),(25,13,3,2,480.00),(26,13,4,1,520.00),(27,14,5,2,200.00),(28,14,6,1,22.00),(29,15,7,1,350.00),(30,15,8,2,5.00),(31,1,1,3,260.00),(32,1,2,2,145.00),(33,2,3,1,480.00),(34,2,4,1,520.00),(35,3,5,1,200.00),(36,3,6,1,22.00),(37,4,7,1,350.00),(38,4,8,2,5.00),(39,5,9,1,55.00),(40,5,10,2,28.00),(41,6,11,2,22.00),(42,6,12,1,45.00),(43,7,13,1,150.00),(44,7,14,1,35.00),(45,8,15,1,45.00),(46,8,16,1,38.00),(47,9,17,1,7.00),(48,9,18,1,18.00),(49,10,19,1,180.00),(50,10,20,2,35.00),(51,11,21,1,22.00),(52,11,22,1,18.00),(53,12,1,2,260.00),(54,12,2,3,145.00),(55,13,3,2,480.00),(56,13,4,1,520.00),(57,14,5,2,200.00),(58,14,6,1,22.00),(59,15,7,1,350.00),(60,15,8,2,5.00),(61,16,1,5,260.00),(62,17,1,5,260.00),(63,18,1,5,260.00);
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_carrito`
--

DROP TABLE IF EXISTS `producto_carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_carrito` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carrito_id` int NOT NULL,
  `producto_id` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `reserva_expira` datetime DEFAULT NULL,
  `estado_reserva` enum('ACTIVA','EXPIRADA','COMPRADA') DEFAULT 'ACTIVA',
  `fecha_agregado` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `carrito_id` (`carrito_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `producto_carrito_ibfk_1` FOREIGN KEY (`carrito_id`) REFERENCES `carrito` (`id`),
  CONSTRAINT `producto_carrito_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_carrito`
--

LOCK TABLES `producto_carrito` WRITE;
/*!40000 ALTER TABLE `producto_carrito` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto_carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `producto` varchar(45) DEFAULT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `descripcion` text,
  `precioCompra` decimal(10,2) DEFAULT NULL,
  `precioVenta` decimal(10,2) DEFAULT NULL,
  `Cantidad_Stock` int DEFAULT '0',
  `imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Panel Solar Monocristalino 400W','Trina Solar.','TS-400M','Alta eficiencia y rendimiento incluso en sombra',150.00,260.00,14,'monocristalino400w.jpg'),(2,'Panel Solar Flexible 100W','Renogy','RNG-FX100','Ideal para caravanas y superficies curvas',85.00,145.00,7,'flexible100w.jpg'),(3,'Inversor Solar 3000W Onda Pura','Growatt','GROW-3000','Convierte la energía solar en corriente alterna',280.00,480.00,6,'inversorSolar3000.png'),(4,'Inversor Híbrido 5000W','Huawei','SUN2000-5KTL','Compatible con baterías y red eléctrica',450.00,750.00,7,'inversorHibrido5000w.png'),(5,'Batería Litio 12V 100Ah','Battle Born','BB-12100','Larga vida útil y carga rápida',450.00,700.00,11,'bateriaLitio12v100a.jpg'),(6,'Batería AGM 12V 200Ah','Trojan','TRO-200AGM','Batería sellada libre de mantenimiento',300.00,520.00,13,'bateriaAGM12v100a.jpg'),(7,'Controlador de Carga MPPT 40A','Victron','VIC-40MPPT','Optimiza la carga de baterías desde el panel',110.00,180.00,14,'controladorMPPT40a.png'),(8,'Controlador PWM 30A','EPEVER','EP-PWM30','Regulador económico para sistemas pequeños',35.00,60.00,19,'controladorPWM30a.jpg'),(9,'Kit Solar Básico 200W','EcoKit','EK-200','Incluye panel, regulador y cableado',220.00,350.00,10,'kitSolarBasico200w.jpg'),(10,'Kit Solar Portátil 100W','Allpowers','AP-K100','Ideal para camping y emergencias',120.00,200.00,13,'kitSolarPortatil100w.jpg'),(11,'Cable Solar 4mm Rojo - 10m','SolarCable','SC-4R10','Cable resistente a rayos UV y altas temperaturas',10.00,18.00,38,'cableSolarRojo4mm10m.jpg'),(12,'Conectores MC4 (par)','SolarTech','MC4-ST','Conectores estándar para paneles solares',2.00,5.00,100,'conectoresMC4.jpg'),(13,'Cable Solar 6mm Negro - 10m','SolarCable','SC-6N10','Aislamiento doble y alta conductividad',12.00,22.00,30,'cableSolarNegro6mm10m.jpg'),(14,'Soporte Inclinación Panel Techo Plano','SunMount','SM-TPF','Aluminio resistente para instalación fija',30.00,55.00,15,'soporteIncliacionTechoPlano.jpg'),(15,'Estructura para 4 Paneles','PanelRack','PR-4P','Estructura ajustable para techo metálico',80.00,140.00,8,'estructura4Paneles.png'),(16,'Fusible Solar 20A','SolarSafe','SS-F20','Protección contra sobrecorrientes',3.00,7.00,50,'fusible20a.png'),(17,'Caja de conexiones IP65','SolarBox','SB-IP65','Protección para conexiones exteriores',15.00,28.00,20,'cajaConexionesIP65.jpg'),(18,'Interruptor DC 1000V 32A','PVSwitch','PVS-32DC','Desconexión segura de corriente continua',20.00,35.00,14,'interruptoprDc32a.jpg'),(19,'Medidor de Energía Solar Digital','Victron','VIC-METER','Monitorea consumo y generación solar',45.00,80.00,10,'medidorDigital.png'),(20,'Sensor de Temperatura para Batería','BatterySense','BS-TEMP','Optimiza la carga según la temperatura',10.00,18.00,25,'sensorTempaeraturaBateria.jpg'),(21,'Crimpadora para conectores solares','ToolTech','TT-CRIMP','Herramienta para MC4 y similares',25.00,45.00,12,'crimpadoraConectorSolar.jpg'),(22,'Pelacables Solar Profesional','WirePro','WP-STRIP','Para cables solares de 2.5 a 6 mm²',12.00,22.00,17,'pelacableSolar.jpg'),(23,'Lámpara Solar LED 100W','GreenLight','GL-100LED','Lámpara exterior con panel integrado',35.00,65.00,22,'lamparaSolar100w.jpg'),(24,'Foco Solar con Sensor de Movimiento','LumiSolar','LS-SENSOR','Activación automática al detectar movimiento',20.00,38.00,30,'focoSolarSensor.png'),(25,'Bomba de Agua Solar 12V','AgroSolar','AS-WP12','Ideal para riego con energía solar',90.00,150.00,7,'bombaAguaSolar12v.jpg'),(27,'Panel Solar','Tech','AS-23','Panel Solar 1000W',500.00,1000.00,7,'panel100w.jpg');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NOMBRE_COMPLETO` varchar(100) NOT NULL,
  `NOMBRE_USUARIO` varchar(50) NOT NULL,
  `DIRECCION` varchar(100) NOT NULL,
  `CORREO` varchar(100) NOT NULL,
  `CONTRASEÑA` varchar(255) NOT NULL,
  `ROL` enum('cliente','admin') NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NOMBRE_USUARIO` (`NOMBRE_USUARIO`),
  UNIQUE KEY `CORREO` (`CORREO`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Ivan Alejandro Ochoa Vega','AOchoa','Tecuala #1910','AOchoa@gmail.com','$2a$10$PjZeLBnu396d0GExQcW.9.iCMscTkVD4l4D6.Yx5pTQGzk/m5Lj1O','admin'),(2,'Issac Andre Arellano','IArellano','Casa Blanca #2019','IArellano@gmail.com','$2a$10$KqNcL/hy0t5Yrg8BkAFe5.PglsbGkwf1JvziTIcgppcAZs9Sb3r9i','admin'),(3,'Carlos Armando Clark','AClark','Paris #100','AClark@gmail.com','$2a$10$Ukxvixdp9ZjdWEJPmZ5OfOP9BJ8RY.rNto/4hKJEo0ELGRhEO.tce','cliente'),(4,'Ivan Guillermo Becerra','IBecerra','Centro #234','IBecerra@gmail.com','$2a$10$UIhgsVjuJJ21HxUeXsfbX.FehxwvOVV3kix0baAX4MuocxsAYQjbq','cliente'),(5,'Victor Manuel Ochoa','VOchoa','Colonia Las Fuentes','VOchoa@gmail.com','$2a$10$o5CkXrv601onujGOyG1JbuqdlvSwUtMv5Bcv0DfwMhLKngyqBAidm','cliente'),(6,'Angel Garcia Gracia','AGarcia','Volcano #334','AGarcia@gmail.com','$2a$10$Mfemz4LeoDWEmWCKOSP5.upaoCVQyBH1RhUIo9Y9ITOOiHqqcChy6','cliente'),(7,'Martin Valdez','MValdez','Obregon #23','mValdez@gmail.com','$2a$10$dggCKWZTXJ.NwHLv/WNcpOXN15aa4gnlVPsYe1NrQZW0hwlpf/lEy','cliente'),(8,'prueba','prueba','prueba123456','prueba12345@gmail.com','$2a$10$7tCYsUFuhVJk6uH/hRKAuu7Ypk/KuNogtHSOdV9AJ7Ct3vySHxkaW','cliente'),(9,'prueba2','prueba2','prueba2 4689','prueba2@gmail.com','$2a$10$iI63FWmxGcQGzY/UwijzeedckZMLK7kM2phv/Ju224tKRgGRxvude','admin');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `usuario_id` int NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,'2025-05-01 10:00:00',3,1500.00),(2,'2025-05-02 11:30:00',4,1000.00),(3,'2025-05-03 15:00:00',5,800.00),(4,'2025-03-04 09:00:00',3,2000.00),(5,'2025-04-05 17:45:00',6,1200.00),(6,'2025-04-06 14:30:00',3,500.00),(7,'2025-04-07 10:00:00',4,1800.00),(8,'2025-04-08 13:15:00',5,750.00),(9,'2025-05-09 16:00:00',3,1200.00),(10,'2025-05-10 12:00:00',4,1500.00),(11,'2025-05-11 10:30:00',5,1000.00),(12,'2025-05-12 11:45:00',3,800.00),(13,'2025-03-13 10:15:00',6,1500.00),(14,'2025-03-14 09:30:00',4,1200.00),(15,'2025-03-15 15:00:00',5,600.00),(16,'2025-05-07 14:34:21',5,1300.00),(17,'2025-05-07 14:42:42',5,1300.00),(18,'2025-05-07 17:04:35',5,1300.00);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'paneles'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_ActualizarCarrito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ActualizarCarrito`(
 IN p_usuario_id INT
)
BEGIN
    DECLARE v_carrito_id INT;
    
    -- Obtener carrito del usuario
    SELECT id INTO v_carrito_id 
    FROM carrito 
    WHERE usuario_id = p_usuario_id
    LIMIT 1;
    
    IF v_carrito_id IS NOT NULL THEN
        -- Devolver al stock productos con reserva expirada
        UPDATE productos p
        JOIN producto_carrito pc ON p.id = pc.producto_id
        SET p.Cantidad_Stock = p.Cantidad_Stock + pc.cantidad
        WHERE pc.carrito_id = v_carrito_id
        AND pc.estado_reserva = 'ACTIVA'
        AND pc.reserva_expira <= NOW();
        
        -- Eliminar productos expirados del carrito
        DELETE FROM producto_carrito
        WHERE carrito_id = v_carrito_id
        AND estado_reserva = 'ACTIVA'
        AND reserva_expira <= NOW();
        
        -- Devolver lista actualizada del carrito
        SELECT 
            pc.producto_id,
            p.producto AS nombre_producto,
            pc.cantidad,
            pc.precio_unitario,
            (pc.cantidad * pc.precio_unitario) AS subtotal,
            pc.reserva_expira,
            CASE 
                WHEN pc.reserva_expira <= NOW() THEN 'EXPIRADA'
                ELSE 'ACTIVA'
            END AS estado_actual
        FROM producto_carrito pc
        JOIN productos p ON pc.producto_id = p.id
        WHERE pc.carrito_id = v_carrito_id;
    END IF;
    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizarProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarProducto`(
    IN p_id INT,
    IN p_producto VARCHAR(100),
    IN p_marca VARCHAR(50),
    IN p_modelo VARCHAR(50),
    IN p_descripcion TEXT,
    IN p_precioCompra DECIMAL(10, 2),
    IN p_precioVenta DECIMAL(10, 2)
)
BEGIN
    UPDATE productos
    SET producto = p_producto,
        marca = p_marca,
        modelo = p_modelo,
        descripcion = p_descripcion,
        precioCompra = p_precioCompra,
        precioVenta = p_precioVenta
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ActualizarUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ActualizarUsuario`(
    IN p_id INT,
    IN p_nombreCompleto VARCHAR(100),
    IN p_nombreUsuario VARCHAR(50),
    IN p_direccion VARCHAR(255),
    IN p_correo VARCHAR(100)
)
BEGIN
    UPDATE USUARIO
    SET NOMBRE_COMPLETO = p_nombreCompleto,
        NOMBRE_USUARIO = p_nombreUsuario,
        DIRECCION = p_direccion,
        CORREO = p_correo
    WHERE ID = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_AgregarProductoCarrito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_AgregarProductoCarrito`(
    IN p_idUsuario INT,  
    IN p_idProducto INT, 
    IN p_cantidad INT 
)
BEGIN
    DECLARE v_carrito_id INT;
    DECLARE v_existente INT;
    DECLARE v_stock_disponible INT;
    DECLARE v_precio_producto DECIMAL(10,2);
    DECLARE v_reserva_activa INT DEFAULT 0;
    
    -- Verificar existencia de usuario y producto
    IF NOT EXISTS (SELECT 1 FROM usuario WHERE ID = p_idUsuario) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El usuario no existe';
    ELSEIF NOT EXISTS (SELECT 1 FROM productos WHERE id = p_idProducto) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El producto no existe';
    ELSE
        -- Calcular stock disponible considerando reservas activas
        SELECT 
            p.Cantidad_Stock - IFNULL(
                (SELECT SUM(pc.cantidad) 
                 FROM producto_carrito pc
                 JOIN carrito c ON pc.carrito_id = c.id
                 WHERE pc.producto_id = p_idProducto
                 AND pc.estado_reserva = 'ACTIVA'
                 AND pc.reserva_expira > NOW()), 0)
        INTO v_stock_disponible
        FROM productos p
        WHERE p.id = p_idProducto;
        
        IF v_stock_disponible < p_cantidad THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stock insuficiente para la cantidad solicitada';
        ELSE
            START TRANSACTION;
            
            -- Obtener o crear carrito
            SELECT id INTO v_carrito_id FROM carrito WHERE usuario_id = p_idUsuario;
            
            IF v_carrito_id IS NULL THEN
                INSERT INTO carrito (usuario_id) VALUES (p_idUsuario);
                SET v_carrito_id = LAST_INSERT_ID();
            END IF;
            
            -- Verificar si ya existe una reserva activa para este producto
            SELECT COUNT(*) INTO v_reserva_activa
            FROM producto_carrito
            WHERE carrito_id = v_carrito_id
            AND producto_id = p_idProducto
            AND estado_reserva = 'ACTIVA'
            AND reserva_expira > NOW();
            
            -- Obtener precio del producto
            SELECT precioVenta INTO v_precio_producto FROM productos WHERE id = p_idProducto;
            
            IF v_reserva_activa > 0 THEN
                -- Actualizar reserva existente (extender tiempo y sumar cantidad)
                UPDATE producto_carrito
                SET cantidad = cantidad + p_cantidad,
                    reserva_expira = DATE_ADD(NOW(), INTERVAL 2 MINUTE)
                WHERE carrito_id = v_carrito_id
                AND producto_id = p_idProducto
                AND estado_reserva = 'ACTIVA';
            ELSE
                -- Crear nueva reserva
                INSERT INTO producto_carrito (
                    carrito_id, 
                    producto_id, 
                    cantidad, 
                    precio_unitario, 
                    reserva_expira, 
                    estado_reserva
                ) VALUES (
                    v_carrito_id,
                    p_idProducto,
                    p_cantidad,
                    v_precio_producto,
                    DATE_ADD(NOW(), INTERVAL 1 MINUTE),
                    'ACTIVA'
                );
                
                -- Descontar del stock solo si es nueva reserva
                UPDATE productos
                SET Cantidad_Stock = Cantidad_Stock - p_cantidad
                WHERE id = p_idProducto;
            END IF;
            
            COMMIT;
        END IF;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_aumentar_stock_producto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_aumentar_stock_producto`(
    IN p_id INT,
    IN p_cantidad INT
)
BEGIN
    UPDATE productos
    SET Cantidad_Stock = Cantidad_Stock + p_cantidad
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ComprarCarrito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ComprarCarrito`(
    IN p_usuario_id INT
)
BEGIN
    DECLARE v_carrito_id INT;
    DECLARE v_total DECIMAL(10,2);
    DECLARE v_venta_id INT;
    DECLARE v_reservas_activas INT;
    DECLARE v_reservas_expiradas INT;

    -- Iniciar transacción
    START TRANSACTION;

    -- Obtener el ID del carrito del usuario
    SELECT id INTO v_carrito_id
    FROM carrito
    WHERE usuario_id = p_usuario_id
    LIMIT 1;

    -- Si no hay carrito, cancelar
    IF v_carrito_id IS NULL THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay carrito para este usuario';
    END IF;

    -- Verificar si hay reservas expiradas en el carrito
    SELECT COUNT(*) INTO v_reservas_expiradas
    FROM producto_carrito
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'ACTIVA'
    AND reserva_expira <= NOW();

    -- Si hay reservas expiradas, cancelar la compra
    IF v_reservas_expiradas > 0 THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Algunos productos en tu carrito han expirado. Por favor, actualiza tu carrito antes de comprar';
    END IF;

    -- Verificar reservas activas válidas
    SELECT COUNT(*) INTO v_reservas_activas
    FROM producto_carrito
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'ACTIVA'
    AND reserva_expira > NOW();

    -- Si no hay productos válidos en el carrito
    IF v_reservas_activas = 0 THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay productos válidos en el carrito';
    END IF;

    -- Calcular el total del carrito (solo productos con reserva activa)
    SELECT SUM(cantidad * precio_unitario)
    INTO v_total
    FROM producto_carrito
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'ACTIVA'
    AND reserva_expira > NOW();

    -- Insertar la venta
    INSERT INTO ventas (fecha, usuario_id, total)
    VALUES (NOW(), p_usuario_id, v_total);

    SET v_venta_id = LAST_INSERT_ID();

    -- Insertar los productos en detalle_venta (solo reservas activas)
    INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario)
    SELECT v_venta_id, producto_id, cantidad, precio_unitario
    FROM producto_carrito
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'ACTIVA'
    AND reserva_expira > NOW();

    -- No necesitamos actualizar el stock aquí porque ya se descontó al agregar al carrito
    -- Solo marcamos como COMPRADA la reserva
    
    -- Marcar productos como COMPRADOS
    UPDATE producto_carrito
    SET estado_reserva = 'COMPRADA'
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'ACTIVA'
    AND reserva_expira > NOW();

    -- Eliminar solo los productos comprados del carrito
    DELETE FROM producto_carrito 
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'COMPRADA';

    -- Eliminar productos expirados (si los hubiera)
    DELETE FROM producto_carrito
    WHERE carrito_id = v_carrito_id
    AND estado_reserva = 'ACTIVA'
    AND reserva_expira <= NOW();

    -- Eliminar el carrito si está vacío
    DELETE FROM carrito 
    WHERE id = v_carrito_id
    AND NOT EXISTS (
        SELECT 1 FROM producto_carrito 
        WHERE carrito_id = v_carrito_id
    );

    -- Devolver información completa de la venta
    SELECT 
        v.id AS venta_id,
        v.fecha,
        v.total,
        dv.producto_id,
        p.producto AS nombre_producto,
        dv.cantidad,
        dv.precio_unitario AS precio_unitario_venta,
        (dv.cantidad * dv.precio_unitario) AS subtotal
    FROM ventas v
    JOIN detalle_venta dv ON v.id = dv.venta_id
    JOIN productos p ON dv.producto_id = p.id
    WHERE v.id = v_venta_id;

    -- Confirmar transacción
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CrearUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_CrearUsuario`(
    IN p_nombreCompleto VARCHAR(100),
    IN p_nombreUsuario VARCHAR(50),
    IN p_direccion VARCHAR(100),
    IN p_correo VARCHAR(100),
    IN p_password VARCHAR(255),
    IN p_rol VARCHAR(20)
)
BEGIN
    INSERT INTO usuario (NOMBRE_COMPLETO, NOMBRE_USUARIO, DIRECCION, CORREO, CONTRASEÑA, ROL)
    VALUES (p_nombreCompleto, p_nombreUsuario, p_direccion, p_correo, p_password, p_rol);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminarProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarProducto`(
    IN p_id INT
)
BEGIN
    DELETE FROM productos WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_EliminarUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminarUsuario`(
    IN p_ID INT
)
BEGIN
    DELETE FROM USUARIO WHERE ID = p_ID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_producto_carrito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminar_producto_carrito`(
    IN p_id_producto_carrito INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Eliminar el producto del carrito
    DELETE FROM producto_carrito 
    WHERE id = p_id_producto_carrito;
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FiltroProductoExistencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_FiltroProductoExistencia`(
    IN p_producto VARCHAR(255)  -- Nuevo parámetro para filtrar por el nombre del producto
)
BEGIN
    SELECT 
        producto AS p_idProducto,
        id,
        producto,
        marca,
        modelo,
        descripcion,
        cantidad_stock
    FROM productos
    WHERE 
        (p_producto IS NULL OR producto LIKE CONCAT('%', p_producto, '%'))  -- Filtro por nombre del producto
        AND Cantidad_Stock > 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertarProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarProducto`(
    IN p_producto VARCHAR(100),
    IN p_marca VARCHAR(50),
    IN p_modelo VARCHAR(50),
    IN p_descripcion TEXT,
    IN p_precioCompra DECIMAL(10, 2),
    IN p_precioVenta DECIMAL(10, 2)
)
BEGIN
    INSERT INTO productos (producto, marca, modelo, descripcion, precioCompra, precioVenta)
    VALUES (p_producto, p_marca, p_modelo, p_descripcion, p_precioCompra, p_precioVenta);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listarProductos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarProductos`()
BEGIN
    SELECT * FROM productos;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listarProductosCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarProductosCliente`()
BEGIN
    SELECT 
        id,
        producto,
        marca,
        modelo,
        descripcion,
        precioVenta,
        Cantidad_Stock
    FROM productos;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listarProductosStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarProductosStock`()
BEGIN
    SELECT 
        id,
        producto,
        modelo,
        Cantidad_Stock AS stock
    FROM productos;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_Loggin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_Loggin`(
    IN p_nombreUsuario VARCHAR(50)
)
BEGIN
    SELECT ID, CONTRASEÑA 
    FROM usuario 
    WHERE NOMBRE_USUARIO = p_nombreUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerListaAdmin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerListaAdmin`()
BEGIN
SELECT 
        ID, 
        NOMBRE_COMPLETO, 
        NOMBRE_USUARIO, 
        DIRECCION, 
        CORREO
    FROM usuario
    WHERE ROL = 'admin';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerListaUsuarios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerListaUsuarios`()
BEGIN
	SELECT 
        ID, 
        NOMBRE_COMPLETO, 
        NOMBRE_USUARIO, 
        DIRECCION, 
        CORREO
    FROM usuario
    WHERE ROL = 'cliente';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerListaUsuariosFinal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerListaUsuariosFinal`()
BEGIN
    SELECT * FROM usuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerProducto`(
    IN p_id INT
)
BEGIN
    SELECT 
        id,
        producto,
        marca,
        modelo,
        descripcion,
        precioCompra,
        precioVenta,
        Cantidad_Stock,
        imagen
    FROM productos
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerRolPorId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerRolPorId`(
    IN p_id INT
)
BEGIN
    SELECT ROL 
    FROM usuario 
    WHERE ID = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtenerUltimaCompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtenerUltimaCompra`(IN usuarioId INT)
BEGIN
    SELECT 
        dc.id,
        dc.producto_id,
        p.marca,
        p.modelo,
        dc.cantidad,
        dc.precio_unitario,
        (dc.cantidad * dc.precio_unitario) AS total_producto
    FROM detalle_venta dc
    JOIN productos p ON dc.producto_id = p.id
    WHERE dc.venta_id = (
        SELECT MAX(id) 
        FROM ventas 
        WHERE usuario_id = usuarioId
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerUsuario`(
    IN p_ID INT
)
BEGIN
    SELECT * FROM USUARIO WHERE ID = p_ID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ObtenerUsuarioPorId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ObtenerUsuarioPorId`(
    IN p_id INT
)
BEGIN
    SELECT * FROM USUARIO WHERE ID = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_precio_compra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_precio_compra`(
    IN p_producto_id INT,
    OUT p_precio_compra DECIMAL(10,2)
)
BEGIN
    SELECT precioCompra INTO p_precio_compra
    FROM productos
    WHERE id = p_producto_id;
    
    IF p_precio_compra IS NULL THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Producto no encontrado o precio no disponible';
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ProductoExistencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ProductoExistencia`(
IN p_idProducto int
)
BEGIN
SELECT 
        id AS p_idProducto,usuario
        marca,
        modelo,
        descripcion,
        Cantidad_Stock AS existencia
    FROM productos
    WHERE id = p_idProducto  
    AND Cantidad_Stock > 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ProductoExistenciaTodos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ProductoExistenciaTodos`()
BEGIN
	    Select id, 
        producto,
        marca,
        modelo,
        descripcion,
        Cantidad_Stock
    FROM productos
    WHERE  Cantidad_Stock > 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_registrar_compra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_registrar_compra`(
    IN p_producto_id INT,
    IN p_admin_id INT,
    IN p_cantidad INT,
    IN p_precio_unitario DECIMAL(10,2)  -- Este parámetro ahora representa el precio UNITARIO del producto
)
BEGIN
    DECLARE v_precio_total DECIMAL(10,2);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Calcular el precio total de la compra (precio unitario * cantidad)
    SET v_precio_total = p_precio_unitario * p_cantidad;
    
    -- Registrar la compra (guardamos el precio TOTAL en la columna precio_unitario)
    INSERT INTO compras (producto_id, admin_id, cantidad, precio_unitario)
    VALUES (p_producto_id, p_admin_id, p_cantidad, v_precio_total);
    
    -- Actualizar el stock del producto
    UPDATE productos
    SET Cantidad_Stock = Cantidad_Stock + p_cantidad
    WHERE id = p_producto_id;
    
    COMMIT;
    
    -- Retornar el ID de la compra registrada
    SELECT LAST_INSERT_ID() AS id_compra;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ReporteVentasPeriodo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ReporteVentasPeriodo`(
    IN fechaInicio DATE,
    IN fechaFin DATE
)
BEGIN
    SELECT 
        v.id AS `ID Venta`,
        v.fecha,
        c.NOMBRE_COMPLETO AS `Cliente`,
        p.producto AS `Producto`,
        p.marca AS `Marca`,
        p.modelo AS `Modelo`,
        dv.cantidad AS `Cantidad Vendida`,
        dv.precio_unitario AS `Precio Unitario`,
        (dv.cantidad * dv.precio_unitario) AS `Total Venta`
    FROM ventas v
    INNER JOIN usuario c ON v.usuario_id = c.id
    INNER JOIN detalle_venta dv ON v.id = dv.venta_id
    INNER JOIN productos p ON dv.producto_id = p.id
    WHERE v.fecha BETWEEN fechaInicio AND fechaFin
    ORDER BY v.fecha;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_TotalCarrito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_TotalCarrito`(
IN p_idUsuario INT
)
BEGIN
	DECLARE v_total DECIMAL(10,2);

    -- Sumar el total de los productos en el carrito
    SELECT SUM(pc.cantidad * pc.precio_unitario) INTO v_total
    FROM producto_carrito pc
    JOIN carrito c ON pc.carrito_id = c.id
    WHERE c.usuario_id = p_idUsuario;
    
    -- Devolver el total calculado
    SELECT v_total AS total_carrito;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_Ventas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Ventas`()
BEGIN
SELECT 
        COUNT(v.id) AS total_ventas,
        SUM(v.total) AS ingresos_totales,
        SUM(dv.cantidad) AS productos_vendidos
    FROM ventas v
    JOIN detalle_venta dv ON v.id = dv.venta_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_VentasPorProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_VentasPorProducto`(
 IN p_idProducto INT
 )
BEGIN
SELECT 
        p.id AS p_idProducto,
        p.producto,
        p.marca,
        p.modelo,
        SUM(dv.cantidad) AS unidades_vendidas,
        SUM(dv.cantidad * dv.precio_unitario) AS total_generado
    FROM detalle_venta dv
    JOIN producto p ON dv.producto_id = p.id
    WHERE p.id = p_idProducto
    GROUP BY p.id, p.producto, p.marca, p.modelo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_VerCarrito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_VerCarrito`(
IN p_idUsuario int
)
BEGIN
SELECT 
        pc.id AS detalle_id,
        p.id AS producto_id,
        p.marca,
        p.modelo,
        pc.cantidad,
        pc.precio_unitario,
        (pc.cantidad * pc.precio_unitario) AS total_producto
    FROM producto_carrito pc
    JOIN productos p ON pc.producto_id = p.id
    JOIN carrito c ON pc.carrito_id = c.id
    WHERE c.usuario_id = p_idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-12 17:55:37
