CREATE TABLE usuario (
    id int auto_increment PRIMARY KEY,
    idpersona int NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (idpersona) references persona(id)
);