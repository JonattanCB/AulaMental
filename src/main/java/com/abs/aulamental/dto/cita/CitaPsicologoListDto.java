package com.abs.aulamental.dto.cita;

public record CitaPsicologoListDto(
        int id,
        String nombre,
        String numero,
        String fecha,
        String hora,
        String estado,
        String motivo
) {
}
