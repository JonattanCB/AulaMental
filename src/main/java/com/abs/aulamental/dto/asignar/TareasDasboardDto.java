package com.abs.aulamental.dto.asignar;

import com.abs.aulamental.model.enums.Tipodocumentacion;

public record TareasDasboardDto(
        String nombre,
        Tipodocumentacion tipodocumentacion,
        String fecha
) {
}
