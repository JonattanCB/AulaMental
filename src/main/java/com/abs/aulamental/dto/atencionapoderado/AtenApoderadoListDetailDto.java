package com.abs.aulamental.dto.atencionapoderado;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Valid
public record AtenApoderadoListDetailDto(
        @NotNull(message = "El id del atencion apoderado no puede ser nulo") int id,
        @NotBlank(message = "El motivo no puede ser nulo") String motivo,
        @NotNull(message = "La fecha no puede ser nula") Date fecha
) {
}
