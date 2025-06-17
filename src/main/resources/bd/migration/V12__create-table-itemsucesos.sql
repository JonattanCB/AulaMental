CREATE TABLE item_sucesos(
    id int auto_increment PRIMARY KEY,
    id_alumno int NOT NULL,
    idsucesos int NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    nivel_gravedad ENUM('BAJA', 'MEDIA', 'ALTA') NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (id_alumno) references alumno(id),
    foreign key (idsucesos) references sucesos(id)
)