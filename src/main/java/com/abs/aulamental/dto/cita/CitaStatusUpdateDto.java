package com.abs.aulamental.dto.cita;

import com.abs.aulamental.model.enums.EstadoCitas;

public record CitaStatusUpdateDto(
        int id,
        EstadoCitas estado,
        String observaciones
) {
}
