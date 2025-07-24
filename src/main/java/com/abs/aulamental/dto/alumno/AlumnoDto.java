package com.abs.aulamental.dto.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.model.enums.Nivel;

import java.util.List;

public record AlumnoDto(
        int id,
        String nombre,
        String grado,
        int edad,
        String apotelefono,
        String direccion,
        int gradoselec,
        Nivel nivel,
        List<ApoderadoDto> apoderadoDtos
) {
}
