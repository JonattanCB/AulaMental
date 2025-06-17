CREATE TABLE asistencia(
    id int auto_increment PRIMARY KEY,
    idusuario int NOT NULL,
    fecha DATE NOT NULL,
    horaingreso TIME NOT NULL,
    estado ENUM('PRESENTE', 'FALTA', 'TARDE') DEFAULT 'PRESENTE',
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (idusuario) references usuario(id)
);