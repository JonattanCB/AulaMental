package com.abs.aulamental.dto.asistencia;

import com.abs.aulamental.model.enums.EstadoAsistencia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalTime;

@Valid
public record AsistenciaDto(
        @NotNull(message = "Falto la fecha de ingreso") int id,
        @NotNull(message = "Falto la fecha de ingreso") Date fechaIngreso,
        @NotNull(message = "Falto la hora de ingreso") LocalTime horaIngreso,
        @NotNull(message = "Falto el estado") EstadoAsistencia estado
) {
}
