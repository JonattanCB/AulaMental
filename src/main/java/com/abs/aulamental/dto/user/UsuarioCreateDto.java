package com.abs.aulamental.dto.user;

import com.abs.aulamental.dto.horario.HorarioDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record UsuarioCreateDto(
        @NotNull(message = "Necesito ingresar todos los datos") int idcreador,

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

        @NotBlank(message = "Necesita ingresar todos los datos") @Email(message = "No esta en formato email") String correo,

        @NotBlank(message = "Necesita ingresar todos los datos")  String contrasenia,

        @NotNull(message = "Necesita ingresar todos los datos") List<Integer> idRoles,

        @NotNull(message = "Necesita ingresar todos los datos") List<HorarioDto> horarios
) {
}
