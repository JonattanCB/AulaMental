package com.abs.aulamental.dto.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoCreateDto;
import com.abs.aulamental.model.enums.Nivel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record CreateAlumnoDto(

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

        @NotNull(message = "Necesita ingresar todos los datos") Nivel nivel,

        @NotNull(message = "Necesita ingresar todos los datos") int grado,

        @NotNull(message = "Necesita asignacion de apoderados")List<ApoderadoCreateDto> apoderados
        ) {
}
