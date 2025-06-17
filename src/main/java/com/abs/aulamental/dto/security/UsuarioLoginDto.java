package com.abs.aulamental.dto.security;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Valid
public record UsuarioLoginDto(
      @NotBlank(message = "El correo no puede estar vacío") @Email(message = "No esta en formato email") String correo,
      @NotBlank(message = "La contraseña no puede estar vacío")  String contrasenia
) {
}
