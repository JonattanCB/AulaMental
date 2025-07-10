package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.sql.Date;

@Valid
public record AsignarListPracticantesDto(
      @NotNull int id,
      @NotNull  Tipodocumentacion tipodocumentacion,
      @NotNull EstadoDocumento estadoDocumento,
      @NonNull Date fecha,
      @NotNull int idDocumento
) {
}
