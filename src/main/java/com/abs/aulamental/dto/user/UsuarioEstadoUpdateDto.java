package com.abs.aulamental.dto.user;

import com.abs.aulamental.model.enums.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record UsuarioEstadoUpdateDto(
        @NotNull(message = "Se necesita datos") int id,
        @NotBlank(message = "Se necesita datos") String nombre,
        @NotBlank(message = "Se necesita datos") Estado estado
) {
}
