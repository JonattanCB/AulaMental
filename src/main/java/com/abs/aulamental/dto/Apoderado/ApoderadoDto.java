package com.abs.aulamental.dto.Apoderado;

public record ApoderadoDto(
        int id,
        String nombre,
        String telefono,
        String correo,
        String direccion,
        String parentesco,
        String contactoAlternativo,
        String ocupacion
) {
}
