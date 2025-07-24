package com.abs.aulamental.dto.user;

import java.time.LocalDate;
import java.time.LocalTime;

public record MensajeriaCitaDto(
        int idAlumno,
        int idpsicologo,
        String psicologo,
        LocalDate fecha,
        LocalTime hora,
        String motivo
) {
}
