CREATE TABLE apoderado(
    id int auto_increment PRIMARY KEY,
    idpersona int NOT NULL,
    idalumno int NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    ocupacion VARCHAR(150) not null ,
    parentesco ENUM('PADRE', 'MADRE', 'APODERADO') DEFAULT 'APODERADO',
    convive ENUM('SI', 'NO') DEFAULT 'SI',
    foreign key (idpersona) references persona(id),
    foreign key (idalumno) references alumno(id)
);