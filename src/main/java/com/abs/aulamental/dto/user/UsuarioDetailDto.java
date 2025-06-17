package com.abs.aulamental.dto.user;

import com.abs.aulamental.model.enums.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record UsuarioDetailDto(
        @NotNull(message = "Necesita ingresar la id") int id,
        @NotBlank(message = "Necesita ingresar todos los datos")  String nombrecompleto,
        @NotBlank(message = "Necesita ingresar todos los datos")  String correo,
        @NotBlank(message = "Requiere datos") String telefono1,
        String telefono2,
        @NotBlank(message = "Necesita ingresar todos los datos")  String fregistro,
        @NotBlank(message = "Necesita ingresar todos los datos") Estado estado,
        @NotNull(message = "Falta datos") List<String> RolesAsignados,
        @NotNull(message = "Falta datos") List<String> HorariosAsignados

) {
}
