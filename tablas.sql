DROP DATABASE IF EXISTS panelSolar;
CREATE DATABASE panelSolar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE panelSolar;

ALTER DATABASE panelSolar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE Usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombreCompleto VARCHAR(75) NOT NULL,
    nombreUsuario VARCHAR(25) NOT NULL,
    direccion VARCHAR(125) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    contraseña VARCHAR(100) NOT NULL,
    rool VARCHAR(15) NOT NULL,
    imagenPerfil VARCHAR(255)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Usuarios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
INSERT INTO Usuarios(nombreCompleto, nombreUsuario, direccion, correo, contraseña, rool, imagenPerfil)
VALUES ("Luis Rafael Lagarda Encinas", "xrapayel", "nose", "luisrafaellagarda@gmail.com", "abcdef123456@", "cliente", NULL);
SELECT * FROM Usuarios;
