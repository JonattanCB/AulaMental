package com.abs.aulamental.dto.user;

public record PracticanteListDetailsDto(
        int id,
        String alias,
        String nombre,
        String telefono,
        String email,
        long cantPendiente,
        long cantCerrados
) {
}
