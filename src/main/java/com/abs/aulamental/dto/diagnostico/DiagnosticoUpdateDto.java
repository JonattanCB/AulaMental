package com.abs.aulamental.dto.diagnostico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record DiagnosticoUpdateDto(
        @NotNull int id,
        @NotBlank String nombre,
        @NotBlank String descripcion
) {
}
