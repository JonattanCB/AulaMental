package com.abs.aulamental.dto.suceso;

import com.abs.aulamental.model.enums.NivelGravedad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Valid
public record ItemSucesoDto(
        @NotNull(message = "Necesita un dato del id") int id,
        @NotBlank(message = "Necesita un dato del nombre")  String nombre,
        @NotNull(message = "Necesita un dato del fecha") Date fecha,
        @NotNull(message = "Necesita un dato del nivel") NivelGravedad nivel
) {
}
