CREATE TABLE usuario_rol(
    id int auto_increment PRIMARY KEY,
    id_usuario int NOT NULL,
    id_rol int NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    foreign key (id_usuario) references usuario(id),
    foreign key (id_rol) references rol(id)
)