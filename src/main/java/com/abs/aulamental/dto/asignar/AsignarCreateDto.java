package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record AsignarCreateDto(
        @NotNull  int idusuario,
        @NotNull int  idpracticante,
        @NotNull  Tipodocumentacion tdocumento,
        @NotNull int iddocumento,
        @NotNull  EstadoDocumento estadoDocumento,
        @NotBlank String observacion
) {
}
