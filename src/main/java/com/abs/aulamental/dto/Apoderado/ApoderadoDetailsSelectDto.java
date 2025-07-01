package com.abs.aulamental.dto.Apoderado;

public record ApoderadoDetailsSelectDto(
        int id,
        String alias,
        String nombrecompleto,
        String tdocumento,
        String ndocumento,
        long cantiAtencion,
        String telefono,
        String ultiAsistencia
) {
}
