package com.abs.aulamental.dto.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;

import java.util.List;

public record AlumnoListDto(
        int id,
        String nombre,
        String grado,
        int edad,
        String apotelefono,
        String direccion
) {
}
