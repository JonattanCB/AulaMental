CREATE  TABLE permiso(
    id int auto_increment PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    url VARCHAR(255),
    label VARCHAR(100) NOT NULL,
    icon  VARCHAR(50) NOT NULL,
    parent_id int,
    rol int,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (rol) references rol(id)
)