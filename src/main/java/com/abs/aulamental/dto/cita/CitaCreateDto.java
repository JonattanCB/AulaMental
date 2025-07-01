package com.abs.aulamental.dto.cita;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaCreateDto(
        LocalDate fecha,
        LocalTime hora,
        int idAlumno,
        int idPsicologo,
        String motivo
) {
}
