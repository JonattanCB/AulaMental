package com.abs.aulamental.dto.atencionapoderado;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Valid
public record AtenApoderadoDetailDto(
        @NotNull(message = "El id del apoderado no puede ser nulo") String Apoderado,
        @NotBlank(message = "El motivo no puede ser nulo") String motivo,
        @NotBlank(message = "El resumen no puede ser nulo") String resumen,
        @NotBlank(message = "Las conclusiones no pueden ser nulas") String conclusiones,
        @NotBlank(message = "Las recomendaciones no pueden ser nulas") String recomendaciones,
        @NotBlank(message = "La intervencion no puede ser nula") String intervencion,
        @NotNull(message = "La fecha no puede ser nula") Date fecha,
        String comentario
) {
}
