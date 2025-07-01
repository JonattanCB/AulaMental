package com.abs.aulamental.dto.atencionalumno;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record AtenAlumnoListDto(
        @NotNull(message = "falta el dato del id") int id,
        @NotBlank(message = "falta el dato del nombre completo") String nombrecompleto,
        @NotBlank(message = "falta el dato Nivel - Seccion") String nivelseccion,
        int edad,
        long cantAtencion,
        String ultimaAtencion,
        @NotBlank(message = "falta el dato de telefono")   String telefono,
        String telefono2
) {
}
