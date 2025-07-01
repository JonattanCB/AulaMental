package com.abs.aulamental.dto.asignar;

public record AsignarPracticanteDetailsDto(
        long total,
        long pendiente,
        long enviados,
        long revicion,
        long completados
) {
}
