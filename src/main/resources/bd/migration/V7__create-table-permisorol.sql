CREATE TABLE PermisoRol(
    id int auto_increment PRIMARY KEY,
    idrol int NOT NULL,
    idpermiso int NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    foreign key (idrol) references rol(id),
    foreign key (idpermiso) references permiso(id)
);