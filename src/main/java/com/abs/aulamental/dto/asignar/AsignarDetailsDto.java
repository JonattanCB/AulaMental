package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AsignarDetailsDto(
        @NotBlank String psicologo,
        @NotBlank String  practicante,
        @NotNull Tipodocumentacion tdocumento,
        @NotNull int iddocumento,
        @NotNull EstadoDocumento estadoDocumento,
        @NotBlank String observacion
) {
}
