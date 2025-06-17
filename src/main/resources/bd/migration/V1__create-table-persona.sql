CREATE TABLE persona (
    id int auto_increment PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apaterno VARCHAR(50) NOT NULL,
    amaterno VARCHAR(50) NOT NULL,
    tdocumento VARCHAR(50) NOT NULL,
    ndocumento VARCHAR(50) NOT NULL,
    telefono1 VARCHAR(50) NOT NULL,
    telefono2 VARCHAR(50),
    correo_personal VARCHAR(100) NOT NULL,
    direccion VARCHAR(500) NOT NULL,
    lnacimiento VARCHAR(250) NOT NULL,
    fnacimiento DATE NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);