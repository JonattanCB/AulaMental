package com.abs.aulamental.dto.asistencia;

import com.abs.aulamental.model.enums.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record AsistenciaUsuarioListDto(
        @NotNull int id,
        @NotBlank String nombre,
        @NotBlank String telefono,
        @NotBlank String ultimaAsistencia,
        @NotNull Estado estado
) {
}
