package com.abs.aulamental.dto.asistencia;

import com.abs.aulamental.model.enums.EstadoAsistencia;

public record AsistenciaDasboardDto(
        int id,
        String nombre,
        String hora,
        EstadoAsistencia estado
) {
}
