package com.abs.aulamental.dto.suceso;

import com.abs.aulamental.model.enums.NivelGravedad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.List;

@Valid
public record SucesoDetailDto(
        @NotNull(message = "Falta el id del usuario") int id,
        @NotNull(message = "Falta el id del usuario") String usuario,
        @NotBlank(message = "Falta agregar nombre") String nombre,
        @NotNull(message = "Falta agregar fecha") Date fecha,
        @NotBlank(message = "Falta agregar descripcion") String detalles,
        @NotBlank(message = "Falta agregar argumentos") String argurmentosAlumno,
        @NotBlank(message = "Falta agregar acciones") String accionesRealizadas,
        @NotBlank(message = "Falta agregar nivelGravedad") NivelGravedad nivelGravedad,
        @NotNull(message = "Falta el alumno") List<SucesoAlumnoDto> alumnosSucesos
) {
}
