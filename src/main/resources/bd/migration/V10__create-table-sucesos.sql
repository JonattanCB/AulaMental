CREATE TABLE sucesos(
    id int auto_increment PRIMARY KEY,
    idusuario int NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    detalles VARCHAR(255) NOT NULL,
    argurmentosalumno VARCHAR(255) NOT NULL,
    accionesrealizadas VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (idusuario) references usuario(id)
)