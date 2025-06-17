CREATE TABLE horario(
    id int auto_increment PRIMARY KEY,
    idusuario int NOT NULL,
    dia varchar(12) NOT NULL,
    horaingreso TIME NOT NULL,
    fregistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fmodificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (idusuario) references usuario(id)
)