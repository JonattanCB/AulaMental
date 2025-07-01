CREATE TABLE asignar (
    id BIGINT  PRIMARY KEY  NOT NULL AUTO_INCREMENT,
    id_usuario int NOT NULL,
    id_practicante int NOT NULL,
    tdocumento ENUM('ATENCIONALUMNO', 'ATENCIONAPODERADO') NOT NULL,
    id_documento BIGINT NOT NULL,
    estado ENUM('PENDIENTE', 'REVISADO','ENVIADO', 'CERRADO') DEFAULT 'PENDIENTE',
    observaciones VARCHAR(500),
    fecha_creacion DATE not null ,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_practicante) REFERENCES usuario(id)
);