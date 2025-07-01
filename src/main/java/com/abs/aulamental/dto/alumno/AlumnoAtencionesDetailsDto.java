package com.abs.aulamental.dto.alumno;

public record AlumnoAtencionesDetailsDto(
        int id,
        String nivel,
        int edad,
        Long cantAsistencia,
        String contact1
) {
}
