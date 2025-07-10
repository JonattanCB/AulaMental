package com.abs.aulamental.dto.asignar;

public record AsignarListTablePracticanteDto(
        int id,
        String nombre,
        String telefono,
        String fecha,
        Long pendiente,
        Long enviados
) {
}
