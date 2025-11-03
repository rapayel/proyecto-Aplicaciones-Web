drop database if exists panelSolar;
create database panelSolar;
use panelSolar;

create table Usuarios(
	id int primary key auto_increment,
    nombreCompleto varchar(75) not null,
    nombreUsuario varchar(25) not null,
    direccion varchar(125) not null,
    correo varchar(50) not null,
    contraseña varchar(20) not null unique,
    rool varchar(15) not null
);