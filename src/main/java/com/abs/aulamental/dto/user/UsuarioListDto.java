package com.abs.aulamental.dto.user;

import com.abs.aulamental.model.enums.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record UsuarioListDto(
        @NotNull(message = "Requiere datos")  int id,
        @NotBlank(message = "Requiere datos") String nombreCompleto,
        @NotBlank(message = "Requiere datos") String email,
        @NotBlank(message = "Requiere datos") String telefono,
        @NotNull(message = "Requiere datos") Estado estado
) {
}
