package com.abs.aulamental.dto.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoUpdateDto;
import com.abs.aulamental.model.enums.Nivel;
import jakarta.validation.Valid;

import java.util.List;

@Valid
public record UpdateAlumnoDto(
        int idAlumno,
        int grado,
        Nivel nivel,
        List<ApoderadoUpdateDto> apoderados
) {
}
