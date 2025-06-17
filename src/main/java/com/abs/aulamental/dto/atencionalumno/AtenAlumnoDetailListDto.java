package com.abs.aulamental.dto.atencionalumno;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Valid
public record AtenAlumnoDetailListDto(
      @NotNull int id,
      @NotNull  Date fecha,
      @NotBlank String motivo
) {
}
