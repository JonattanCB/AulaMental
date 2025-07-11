package com.abs.aulamental.dto.alumno;

public record AlumnoListDto(
        int id,
        String nombre,
        String grado,
        int edad,
        String apotelefono,
        String direccion
) {
}
