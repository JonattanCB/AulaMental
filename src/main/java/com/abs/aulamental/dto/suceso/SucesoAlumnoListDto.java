package com.abs.aulamental.dto.suceso;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record SucesoAlumnoListDto(
        @NotNull(message = "falta el dato del id") int id,
        @NotBlank(message = "falta el dato del nombre completo") String nombrecompleto,
        @NotBlank(message = "falta el dato Nivel - Seccion") String nivelseccion,
        @NotBlank(message = "falta el dato de telefono")   String telefono,
        String telefono2
) {
}
