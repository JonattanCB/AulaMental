package com.abs.aulamental.dto.horario;

import com.abs.aulamental.model.enums.Dias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@Valid
public record HorarioUsuarioDto(
        @NotNull(message = "Falta ingresar el dia asignado") Dias dias,
        @NotBlank(message = "Falta la hora asignada para el usuario") LocalTime hora,
        @NotBlank(message = "Falta el diminutvo") String diminituvo
) {
}
