package com.abs.aulamental.dto.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;

import java.util.List;

public record AlumnoDto(
        int id,
        String nombre,
        String grado,
        int edad,
        String apotelefono,
        String direccion,
        List<ApoderadoDto> apoderadoDtos
) {
}
