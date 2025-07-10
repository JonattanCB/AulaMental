package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.EstadoDocumento;

public record TareasDasrboardPracticanteDto(
        int id,
        String titulo,
        String fecha,
        EstadoDocumento estadoDocumento
) {
}
