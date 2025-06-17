package com.abs.aulamental.dto.suceso;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record SucesoAlumnoDto(
        @NotNull(message = "Falta el id del usuario") int idAlumno,
        @NotBlank(message = "Falta agregar motivo") String motivo
) {
}
