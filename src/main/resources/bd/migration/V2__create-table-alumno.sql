CREATE TABLE alumno(
    id int auto_increment PRIMARY KEY,
    idpersona int NOT NULL,
    nivel ENUM('INICIAL', 'PRIMARIA', 'SECUNDARIA') DEFAULT 'PRIMARIA',
    grado int not null,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    foreign key (idpersona) references persona(id)
);