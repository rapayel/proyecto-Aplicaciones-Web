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





