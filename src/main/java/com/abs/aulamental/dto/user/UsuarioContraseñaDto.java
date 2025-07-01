package com.abs.aulamental.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record UsuarioContraseñaDto(
   @NotBlank String contraseñaActual,
   @NotBlank String nuevaContraseña,
   @NotBlank String confirmarContraseña
) {
}
