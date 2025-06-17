package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.Tipodocumentacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Valid
public record AsignarCreateUserDto(
        @NotNull(message = "no idPersona") int idPersonal,
        @NotNull(message = "no idusuario")  int idPsicologo,
        @NotNull(message = "no idPracticante")  int idPracticante,
        @NotNull(message = "no tipo de documentacion")Tipodocumentacion tipodocumentacion
) {
}
