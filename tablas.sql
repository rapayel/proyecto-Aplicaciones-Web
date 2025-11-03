drop database if exists Fruteria;
create database Fruteria;
use fruteria;
create table Clientes(
	id int(11) primary key auto_increment,
    Usuario varchar(100) not null,
    contrasena varchar(12) not null,
	saldo decimal (10,2) default 0.00
);

create table Trabajadores(
	id int(11) primary key auto_increment,
    rfc varchar(15) not null,
    Curp varchar(18) not null,
    Usuario varchar(100) not null,
    contrasena varchar(12) not null,
	tipo varchar(20) not null
);


create table ProductosAlmacen(
	id int(11) primary key auto_increment,
    nombre varchar(20) not null,
    precio decimal(10,2) not null,
    cantKg decimal(10,2)default 0.0000
);

create table ProductosComprados(
	id int(11) primary key auto_increment,
    idCliente int (11),
    nombre varchar(20) not null,
    precio decimal(10,2) not null,
    cantKg decimal(10,2)default 0.00,
    foreign key (idCliente) references Clientes(id) on delete cascade
);

