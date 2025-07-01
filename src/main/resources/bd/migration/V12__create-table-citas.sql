CREATE TABLE cita(
    id int AUTO_INCREMENT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    idalumno int NOT NULL,
    idpsicologo int NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'CANCELADA') NOT NULL,
    observaciones VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (idalumno) REFERENCES alumno(id),
    FOREIGN KEY (idpsicologo) REFERENCES usuario(id)
)