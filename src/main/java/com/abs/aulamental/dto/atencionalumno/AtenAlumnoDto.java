package com.abs.aulamental.dto.atencionalumno;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record AtenAlumnoDto(
        int id,
        @NotBlank String nombre,
        @NotBlank String motivo,
        @NotBlank String resumen,
        @NotBlank String conclusion,
        @NotBlank String recomendacion,
        @NotBlank String tecnicas,
        @NotBlank String comentario,
        @NotBlank String Diagnostico
) {
}
