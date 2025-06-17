package com.abs.aulamental.dto.diagnostico;

import com.abs.aulamental.model.enums.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record DiagnosticoDto(
        @NotNull int id,
        @NotBlank String nombre,
        @NotBlank String descripcion,
        @NotNull Estado estado
) {
}
