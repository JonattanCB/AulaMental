package com.abs.aulamental.dto.security;

public record CambioContraseniaCodeDto(
        String correo,
        String codigo,
        String nuevaContrasena,
        String repetirContrasena
) {
}
