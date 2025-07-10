
INSERT INTO rol(nombre, descripcion, fregistro, fmodificacion, estado)
VALUES
    ('Psicologia', 'Rol para los psicologos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    ('Practicante', 'Rol para los practicas de psicologia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    ('Directora', 'Rol para los directores de la institucion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    ('Bienestar', 'Rol para los bienestar de la institucion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    ('Estudiante', 'Rol para los estudiantes de la institucion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');


-- Insertar datos de ejemplo en las tablas Usuario
INSERT INTO persona(id, nombre, apaterno, amaterno, tdocumento, ndocumento, telefono1, telefono2, correo_personal, direccion, lnacimiento, fnacimiento, fregistro, fmodificacion, estado)
VALUES
    (26, 'Carlos', 'Vargas', 'López', 'DNI', '30111222', '902000001', '902000002', 'carlosv@ejemplo.com', 'Av. Andes 101', 'Lima', '1988-06-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (27, 'Lucía', 'Fernández', 'Díaz', 'DNI', '30111223', '902000003', '902000004', 'luciaf@ejemplo.com', 'Av. Viento 202', 'Lima', '1991-08-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (28, 'Raúl', 'Gómez', 'Pérez', 'DNI', '30111224', '902000005', NULL, 'raulg@ejemplo.com', 'Av. Mar 303', 'Arequipa', '1995-03-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (29, 'Sandra', 'Mora', 'Salas', 'DNI', '30111225', '902000006', NULL, 'sandram@ejemplo.com', 'Av. Sol 404', 'Cusco', '1987-11-25', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (30, 'Hugo', 'Torres', 'Romero', 'DNI', '30111226', '902000007', NULL, 'hugot@ejemplo.com', 'Av. Luna 505', 'Trujillo', '1992-12-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');


INSERT INTO usuario(idpersona, correo, contrasena, estado, fregistro, fmodificacion)
VALUES
    (26, 'carlos@empresa.com', '$2a$12$25xD4hVc4VzndoN7we0xxu32XLyluQEn.8J/ndvZgdzwANN40ALJW', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (27, 'lucia@empresa.com', '$2a$12$25xD4hVc4VzndoN7we0xxu32XLyluQEn.8J/ndvZgdzwANN40ALJW', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (28, 'raul@empresa.com', '$2a$12$25xD4hVc4VzndoN7we0xxu32XLyluQEn.8J/ndvZgdzwANN40ALJW', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (29, 'sandra@empresa.com', '$2a$12$25xD4hVc4VzndoN7we0xxu32XLyluQEn.8J/ndvZgdzwANN40ALJW', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (30, 'hugo@empresa.com', '$2a$12$25xD4hVc4VzndoN7we0xxu32XLyluQEn.8J/ndvZgdzwANN40ALJW', 'ACTIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO usuario_rol(id_usuario, id_rol, fregistro, fmodificacion, estado)
VALUES
    (4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),  -- Bienestar
    (5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),  -- Estudiante
    (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),  -- Psicología
    (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),  -- Practicante
    (3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');  -- Directora



INSERT INTO horario(idusuario, dia, horaingreso, fregistro, fmodificacion)
VALUES
    (4, 'LUNES', '09:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'MIERCOLES', '09:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'VIERNES', '09:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'MARTES', '10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'JUEVES', '10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- Asignar Alumnos a los usuarios


INSERT INTO persona (id, nombre, apaterno, amaterno, tdocumento, ndocumento, telefono1, telefono2, correo_personal, direccion, lnacimiento, fnacimiento, fregistro, fmodificacion, estado)
VALUES
    (31, 'Bruno', 'Salazar', 'Mejía', 'DNI', '31111111', '903000001', NULL, 'bruno@alumno.com', 'Jr. Palmeras 111', 'Lima', '2011-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (32, 'Camila', 'Rojas', 'Fernández', 'DNI', '32222222', '903000002', NULL, 'camila@alumna.com', 'Jr. Sauce 222', 'Cusco', '2012-03-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');

INSERT INTO alumno (idpersona, nivel, grado)
VALUES
    (31, 'PRIMARIA', 4),
    (32, 'PRIMARIA', 3);
-- Apoderado

INSERT INTO persona (id, nombre, apaterno, amaterno, tdocumento, ndocumento, telefono1, correo_personal, direccion, lnacimiento, fnacimiento, fregistro, fmodificacion, estado)
VALUES
    (33, 'Martín', 'Salazar', 'Torres', 'DNI', '33111111', '904000001', 'martin@apoderado.com', 'Av. Roca 101', 'Lima', '1980-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (34, 'Carmen', 'Mejía', 'Gutiérrez', 'DNI', '34111111', '904000002', 'carmen@apoderado.com', 'Av. Roca 101', 'Lima', '1982-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (35, 'Roberto', 'Rojas', 'Mendoza', 'DNI', '35111111', '904000003', 'roberto@apoderado.com', 'Av. Sol 505', 'Cusco', '1979-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO'),
    (36, 'Silvia', 'Fernández', 'Luna', 'DNI', '36111111', '904000004', 'silvia@apoderado.com', 'Av. Sol 505', 'Cusco', '1984-04-04', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVO');

INSERT INTO apoderado (idpersona, idalumno, ocupacion, parentesco, convive)
VALUES
    (33, 1, 'Arquitecto', 'PADRE', 'SI'),
    (34, 1, 'Ingeniera Civil', 'MADRE', 'SI'),
    (35, 2, 'Mecánico', 'PADRE', 'NO'),
    (36, 2, 'Enfermera', 'MADRE', 'SI');


-- PERMISOS

-- Cabeceras para Psicología (Rol ID 1) según tus grupos:
INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol) VALUES
    ('Gestión Usuario', NULL, 'Gestión Usuario', 'bi bi-person', 0, 'ACTIVO', 1),
    ('Gestión Alumnos', NULL, 'Gestión Alumnos', 'bi bi-people', 0, 'ACTIVO', 1),
    ('Gestión de Citas', NULL, 'Gestión de Citas', 'bi bi-calendar', 0, 'ACTIVO', 1),
    ('Gestión de Atención', NULL, 'Gestión de Atención', 'bi bi-person-lines-fill', 0, 'ACTIVO', 1),
    ('Bienestar Estudiantil', NULL, 'Bienestar Estudiantil', 'bi bi-file-earmark-medical', 0, 'ACTIVO', 1),
-- Submenús de Psicología (Rol ID 1) agrupados:
    ('Usuarios', '/usuario', 'Usuarios', 'bi bi-people-fill', 1, 'ACTIVO', 1),
    ('Asistencias', '/asistencia', 'Asistencias', 'pi pi-calendar', 1, 'ACTIVO', 1),
    ('Tareas', '/tareas', 'Tareas', 'pi pi-list', 1, 'ACTIVO', 1),
    ('Alumnos', '/alumnos', 'Alumnos', 'bi bi-mortarboard-fill', 2, 'ACTIVO', 1),
    ('Gestionar Citas', '/citaPsicologo', 'Gestionar Citas', 'bi bi-calendar4-week', 3, 'ACTIVO', 1),
    ('Atención Alumno', '/atenAlumno', 'Atención Alumno', 'bi bi-journal-medical', 4, 'ACTIVO', 1),
    ('Atención Apoderado', '/atenApoderado', 'Atención Apoderado', 'bi bi-journal-medical', 4, 'ACTIVO', 1),
    ('Diagnóstico', '/diagnostico', 'Diagnóstico', 'bi bi-activity', 5, 'ACTIVO', 1);

-- Los demás roles quedan tal como estaban:
-- Practicante (Rol ID 2)
INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol) VALUES
    ('Tareas Practicante', '/tareasPracticante', 'Lista de Tareas', 'bi bi-journal-check', 99, 'ACTIVO', 2);

-- Directora (Rol ID 3)
INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol) VALUES
    ('Gestión Usuario', NULL, 'Gestión Usuario', 'bi bi-person', 0, 'ACTIVO', 3),
    ('Gestión Alumnos', NULL, 'Gestión Alumnos', 'bi bi-people', 0, 'ACTIVO', 3),
    ('Gestión Sucesos', NULL, 'Gestión Sucesos', 'bi bi-exclamation-triangle', 0, 'ACTIVO', 3),
-- Submenús bajo sus respectivos encabezados
    ('Usuarios', '/usuario', 'Usuarios', 'bi bi-people-fill', 15, 'ACTIVO', 3),
    ('Asistencias', '/asistencia', 'Asistencias', 'pi pi-calendar', 15, 'ACTIVO', 3),
    ('Alumnos', '/alumnos', 'Alumnos', 'bi bi-mortarboard-fill', 16, 'ACTIVO', 3),
    ('Sucesos', '/suceso', 'Sucesos', 'bi bi-journal-bookmark-fill', 17, 'ACTIVO', 3);


-- Bienestar (Rol ID 4)

INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol) VALUES
    ('Alumnos', '/alumnos', 'Gestión Alumnos', 'bi bi-people', 99, 'ACTIVO', 4),
    ('Sucesos', '/suceso', 'Gestión Sucesos', 'bi bi-journals',4 , 'ACTIVO', 4);

-- Estudiante (Rol ID 5)
INSERT INTO permiso (nombre, url, label, icon, parent_id, estado, rol) VALUES
    ('Citas', '/cita', 'Citas', 'bi bi-calendar2', 0, 'ACTIVO', 5);