package com.abs.aulamental.dto.suceso;

import com.abs.aulamental.model.enums.NivelGravedad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record SucesoCreateDto(
        @NotNull(message = "Falta el id del usuario") int idUsuario,
        @NotBlank(message = "Falta agregar nombre") String nombre,
        @NotBlank(message = "Falta agregar descripcion") String detalles,
        @NotBlank(message = "Falta agregar argumentos") String argurmentosAlumno,
        @NotBlank(message = "Falta agregar acciones") String accionesRealizadas,
        @NotNull(message = "Falta agregar nivelGravedad") NivelGravedad nivelGravedad,
        @NotNull(message = "Falta el alumno") List<SucesoAlumnoDto> alumnoIncidentes
) {
}
