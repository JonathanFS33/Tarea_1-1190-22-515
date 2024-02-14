/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Jonathan
 * Created: Feb 10, 2024
 */

CREATE DATABASE Pacientes;

CREATE TABLE Paciente(
    id integer AUTO_INCREMENT PRIMARY KEY,
    nombre varchar(100) not null,
    edad int,
    diagnostico varchar(250) not null,
    telefono int,
    direccion varchar(100) not null
)