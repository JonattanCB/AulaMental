package com.abs.aulamental.dto.atencionalumno;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record AtenAlumnoCreateDto(
        @NotNull int idAlumno,
        @NotNull int idUsuario,
        @NotBlank String motivo,
        @NotBlank String resumen,
        @NotBlank String conclusion,
        @NotBlank String recomendacion,
        @NotBlank String tecnicas,
        @NotBlank String comentario,
        @NotNull int idDiagnostico
) {
}
