package com.abs.aulamental.dto.asistencia;

import jakarta.validation.Valid;

@Valid
public record AsistenciaUsuarioDetailsDto(
        String alias,
        long cantPresente,
        long cantTardanza,
        long cantFalto
) {
}
