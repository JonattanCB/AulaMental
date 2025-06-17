CREATE TABLE atencionalumnos(
    id int auto_increment PRIMARY KEY,
    idalumno int NOT NULL,
    motivo VARCHAR(500) ,
    resumen VARCHAR(500) ,
    conclusion VARCHAR(500),
    recomendacion VARCHAR(500),
    tecnicas VARCHAR(500),
    iddiagnostico int NULL,
    comentario VARCHAR(500),
    fecha DATE NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (idalumno) references alumno(id),
    foreign key (iddiagnostico) references diagnostico(id)
)