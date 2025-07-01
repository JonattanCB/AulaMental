package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Valid
public record AsignarTaskListDto(
        @NotNull int id,
        @NotBlank String nombre,
        Tipodocumentacion tipodocumentacion,
        @NotNull EstadoDocumento estadoDocumento,
        @NotNull Date fecha,
        @NotNull int idDocumento,
        String observacion
) {
}
