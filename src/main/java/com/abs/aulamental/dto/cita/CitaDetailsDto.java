package com.abs.aulamental.dto.cita;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaDetailsDto(
        int id,
        LocalDate fecha,
        LocalTime hora,
        int idAlumno,
        int idPsicologo,
        String motivo,
        String estado,
        String observaciones
) {
}
