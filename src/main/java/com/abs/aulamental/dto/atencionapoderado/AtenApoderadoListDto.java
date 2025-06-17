package com.abs.aulamental.dto.atencionapoderado;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record AtenApoderadoListDto(
        @NotNull(message = "falta el dato del id") int id,
        @NotBlank(message = "falta el dato del nombre completo") String nombrecompleto,
        @NotBlank(message = "falta el dato de tdocumento") String tdocumento,
        @NotBlank(message = "falta el dato de ndocumento") String ndocumento,
        @NotBlank(message = "falta el dato de telefono")   String telefono,
        String telefono2
) {
}
