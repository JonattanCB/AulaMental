CREATE TABLE atencion_apoderados (
    id int auto_increment PRIMARY KEY,
    apoderado_id int NOT NULL,
    motivo VARCHAR(500) NOT NULL,
    resumen varchar(500) NOT NULL,
    conclusiones varchar(500) NOT NULL,
    recomendaciones varchar(500) NOT NULL,
    intervencion varchar(500) NOT NULL,
    fecha DATE NOT NULL,
    comentario varchar(500) NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (apoderado_id) references apoderado(id)
);