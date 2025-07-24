package com.abs.aulamental.dto.Apoderado;

import com.abs.aulamental.model.enums.Parentesco;
import com.abs.aulamental.model.enums.SiNo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApoderadoCreateDto(
        @NotBlank(message = "Necesita ingresar todos los datos") String nombre,

        @NotBlank(message = "Necesita ingresar todos los datos") String apaterno,

        @NotBlank(message = "Necesita ingresar todos los datos") String amaterno,

        @NotBlank(message = "Necesita ingresar todos los datos") String tdocumento,

        @NotBlank(message = "Necesita ingresar todos los datos") String ndocumento,

        @NotBlank(message = "Necesita ingresar todos los datos") String telefono1,

        String telefono2,

        @NotBlank(message = "Necesita ingresar todos los datos") @Email(message = "No esta en formato email") String correopersonal,

        @NotBlank(message = "Necesita ingresar todos los datos") String direccion,

        @NotBlank(message = "Necesita ingresar todos los datos") String lnacimiento,

        @NotBlank(message = "Necesita ingresar todos los datos") String fnacimiento,

        @NotBlank(message = "Necesita ingresar todos los datos") String ocupacion,

        @NotNull(message = "Necesita ingresar todos los datos")Parentesco parentesco,

        @NotNull(message = "Necesita ingresar todos los datos") SiNo convive

) {
}
