INSERT INTO rol(nombre, descripcion, fregistro, fmodificacion, estado) VALUES
            ('Psicologia', 'Rol para los psicologos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
            ('Practicante', 'Rol para los practicas de psicologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
            ('Directora', 'Rol para los directores de la institucion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
            ('Bienestar', 'Rol para los bienestar de la institucion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
            ('Estudiante', 'Rol para los estudiantes de la institucion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');


INSERT INTO persona(nombre,apaterno,amaterno,tdocumento,ndocumento,telefono1,telefono2,correo_personal,direccion,lnacimiento,fnacimiento,fregistro,fmodificacion,estado) VALUES
            ('Juan','Lopez','Lopez','DNI','12345678','987654321','987654321','persona1@gmail.com','Av. Los Olivos 123','Lima','1990-01-01',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ACTIVO'),
            ('Maria','Gonzales','Lopez','DNI','87654321','987654321','987654321','persona2@gmail.com','Av. Los Olivos 123','Lima','1990-01-01',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ACTIVO'),
            ('Pedro','Lopez','Lopez','DNI','12345678','987654321','987654321','persona3@gmail.com','Av. Los Olivos 123','Lima','1990-01-01',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ACTIVO'),
            ('Ana','Lopez','Lopez','DNI','12345678','987654321','987654321','persona4@gmail.com','Av. Los Olivos 123','Lima','1990-01-01',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ACTIVO'),
            ('Luis','Lopez','Lopez','DNI','12345678','987654321','987654321','persona5@gmai.com','Av. Los Olivos 123','Lima','1990-01-01',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'ACTIVO');


INSERT INTO  usuario(idpersona, correo, contrasena, estado, fregistro, fmodificacion) VALUES
            (1, 'juan@empresa.com', '$2a$12$PND.gWTGBHIhSbkdJapyV.6fWcB8S80WPQ4URDSdtHcZG4BHDNGi.', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'perez@empresa.com', '$2a$12$PND.gWTGBHIhSbkdJapyV.6fWcB8S80WPQ4URDSdtHcZG4BHDNGi.', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (3, 'lopez@empresa.com', '$2a$12$PND.gWTGBHIhSbkdJapyV.6fWcB8S80WPQ4URDSdtHcZG4BHDNGi.', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO usuario_rol(id_usuario, id_rol, fregistro, fmodificacion, estado) VALUES
            (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
            (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
            (3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');


INSERT  INTO  horario(idusuario, dia, horaingreso, fregistro, fmodificacion) VALUES
            (1, 'LUNES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (1, 'MARTES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (1, 'MIERCOLES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (1, 'JUEVES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (1, 'VIERNES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'LUNES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'MARTES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'MIERCOLES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'JUEVES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            (2, 'VIERNES', '08:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO persona ( id, nombre, apaterno, amaterno, tdocumento, ndocumento, telefono1, telefono2, correo_personal, direccion, lnacimiento, fnacimiento ) VALUES
      (6,'Ana', 'López', 'Martínez', 'DNI', '11111111', '900000001', NULL, 'ana@example.com', 'Calle A 123', 'Lima', '2012-01-10'),
      (7,'Luis', 'García', 'Ramos', 'DNI', '22222222', '900000002', NULL, 'luis@example.com', 'Calle B 234', 'Arequipa', '2011-03-15'),
      (8,'María', 'Torres', 'Salas', 'DNI', '33333333', '900000003', NULL, 'maria@example.com', 'Calle C 345', 'Cusco', '2010-07-20'),
      (9,'Carlos', 'Fernández', 'Quispe', 'DNI', '44444444', '900000004', NULL, 'carlos@example.com', 'Calle D 456', 'Trujillo', '2009-02-25'),
      (10,'Lucía', 'Ramírez', 'Flores', 'DNI', '55555555', '900000005', NULL, 'lucia@example.com', 'Calle E 567', 'Chiclayo', '2013-11-12'),
      (11,'Pedro', 'Castro', 'Morales', 'DNI', '66666666', '900000006', NULL, 'pedro@example.com', 'Calle F 678', 'Lima', '2011-06-30'),
      (12,'Sofía', 'Reyes', 'Lopez', 'DNI', '77777777', '900000007', NULL, 'sofia@example.com', 'Calle G 789', 'Tacna', '2012-08-05'),
      (13,'Diego', 'Vargas', 'Chávez', 'DNI', '88888888', '900000008', NULL, 'diego@example.com', 'Calle H 890', 'Piura', '2010-04-18'),
      (14,'Valeria', 'Mendoza', 'Aguilar', 'DNI', '99999999', '900000009', NULL, 'valeria@example.com', 'Calle I 901', 'Iquitos', '2011-09-09'),
      (15,'Javier', 'Silva', 'Mejía', 'DNI', '10101010', '900000010', NULL, 'javier@example.com', 'Calle J 012', 'Puno', '2012-12-01');

-- Insertar en alumno usando los IDs del 1 al 10
INSERT INTO alumno (idpersona, nivel, grado) VALUES
            (6, 'PRIMARIA', 2),
            (7, 'PRIMARIA', 2),
            (8, 'PRIMARIA', 3),
            (9, 'PRIMARIA', 4),
            (10, 'PRIMARIA', 5),
            (11, 'SECUNDARIA', 4),
            (12, 'PRIMARIA', 1),
            (13, 'PRIMARIA', 2),
            (14, 'PRIMARIA', 3),
            (15, 'PRIMARIA', 4);

INSERT INTO persona (
    nombre, apaterno, amaterno, tdocumento, ndocumento,
    telefono1, telefono2, correo_personal, direccion,
    lnacimiento, fnacimiento
) VALUES
      ('Marcos', 'López', 'Torres', 'DNI', '20111111', '901000001', NULL, 'marcos@example.com', 'Av. Sol 101', 'Lima', '1980-01-01'),
      ('Patricia', 'García', 'Díaz', 'DNI', '20222222', '901000002', NULL, 'patricia@example.com', 'Av. Luna 102', 'Lima', '1982-02-02'),
      ('José', 'Martínez', 'Rojas', 'DNI', '20333333', '901000003', NULL, 'jose@example.com', 'Av. Estrella 103', 'Lima', '1979-03-03'),
      ('Elena', 'Fernández', 'Campos', 'DNI', '20444444', '901000004', NULL, 'elena@example.com', 'Av. Río 104', 'Lima', '1985-04-04'),
      ('Ricardo', 'Ramírez', 'Paredes', 'DNI', '20555555', '901000005', NULL, 'ricardo@example.com', 'Av. Lago 105', 'Lima', '1983-05-05'),
      ('Claudia', 'Castro', 'Velásquez', 'DNI', '20666666', '901000006', NULL, 'claudia@example.com', 'Av. Monte 106', 'Lima', '1981-06-06'),
      ('Alberto', 'Reyes', 'Moreno', 'DNI', '20777777', '901000007', NULL, 'alberto@example.com', 'Av. Cielo 107', 'Lima', '1980-07-07'),
      ('Rosa', 'Vargas', 'Luna', 'DNI', '20888888', '901000008', NULL, 'rosa@example.com', 'Av. Nube 108', 'Lima', '1984-08-08'),
      ('Julio', 'Mendoza', 'Cruz', 'DNI', '20999999', '901000009', NULL, 'julio@example.com', 'Av. Mar 109', 'Lima', '1987-09-09'),
      ('Teresa', 'Silva', 'Ríos', 'DNI', '21010101', '901000010', NULL, 'teresa@example.com', 'Av. Tierra 110', 'Lima', '1986-10-10');

-- Insertar apoderados (vinculando a alumnos con IDs 1–10)
INSERT INTO apoderado (
    idpersona, idalumno, ocupacion, parentesco, convive
) VALUES
      (16, 1, 'Ingeniero', 'PADRE', 'SI'),
      (17, 2, 'Docente', 'MADRE', 'SI'),
      (18, 3, 'Comerciante', 'PADRE', 'NO'),
      (19, 4, 'Abogada', 'MADRE', 'SI'),
      (20, 5, 'Contador', 'PADRE', 'NO'),
      (21, 6, 'Enfermera', 'MADRE', 'SI'),
      (22, 7, 'Chofer', 'PADRE', 'SI'),
      (23, 8, 'Administradora', 'MADRE', 'SI'),
      (24, 9, 'Técnico', 'PADRE', 'NO'),
      (25, 10, 'Secretaria', 'MADRE', 'SI');















-- Cabecera principal
INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol)
VALUES ('UI_COMPONENTS', NULL, 'UI COMPONENTS', 'layout', 0, 'ACTIVO', 1);

-- Hijos bajo UI COMPONENTS (asumimos id=1 para UI_COMPONENTS)
INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol)
VALUES
    ('FORM_LAYOUT', '/form', 'Form Layout', 'form', 1, 'ACTIVO', 1),
    ('INPUT', '/input', 'Input', 'input', 1, 'ACTIVO', 1),
    ('BUTTON', '/button', 'Button', 'button', 1, 'ACTIVO', 1),
    ('TABLE', '/table', 'Table', 'table', 1, 'ACTIVO', 1),
    ('LIST', '/list', 'List', 'list', 1, 'ACTIVO', 1),
    ('TREE', '/tree', 'Tree', 'tree', 1, 'ACTIVO', 1),
    ('PANEL', '/panel', 'Panel', 'panel', 1, 'ACTIVO', 1),
    ('OVERLAY', '/overlay', 'Overlay', 'overlay', 1, 'ACTIVO', 1),
    ('MEDIA', '/media', 'Media', 'media', 1, 'ACTIVO', 1),
    ('MENU', '/menu', 'Menu', 'menu', 1, 'ACTIVO', 1),
    ('MESSAGE', '/message', 'Message', 'message', 1, 'ACTIVO', 1),
    ('FILE', '/file', 'File', 'file', 1, 'ACTIVO', 1),
    ('CHART', '/chart', 'Chart', 'chart', 1, 'ACTIVO', 1),
    ('TIMELINE', '/timeline', 'Timeline', 'timeline', 1, 'ACTIVO', 1),
    ('MISC', '/misc', 'Misc', 'misc', 1, 'ACTIVO', 1);

