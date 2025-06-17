package com.abs.aulamental.dto.atencionalumno;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record AtenAlumnoDto(
        @NotNull  int id,
        @NotBlank String motivo,
        @NotBlank String resumen,
        @NotBlank String conclusion,
        @NotBlank String recomendacion,
        @NotBlank String tecnicas,
        @NotBlank String comentario,
        @NotBlank String Diagnostico
) {
}
