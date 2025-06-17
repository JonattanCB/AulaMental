package com.abs.aulamental.dto.suceso;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;

@Valid
public record SucesoDto(
        @NotBlank(message = "Falta datos")  String nombre,
        @NotBlank(message = "Falta datos")  String detalle,
        @NotBlank(message = "Falta datos")  String argumentos,
        @NotBlank(message = "Falta datos")  String acciones,
        @NotBlank(message = "Falta datos") Date fecha
) {
}
