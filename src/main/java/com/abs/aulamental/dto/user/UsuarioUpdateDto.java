package com.abs.aulamental.dto.user;

import com.abs.aulamental.dto.horario.HorarioDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record UsuarioUpdateDto(
        @NotNull(message = "Necesita ingresar todos los datos")  int idcreador,
        @NotNull(message = "Necesita ingresar todos los datos")  int id,

        @NotBlank(message = "Necesita ingresar todos los datos") String telefono1,

        String telefono2,

        @NotBlank(message = "Necesita ingresar todos los datos") @Email(message = "No esta en formato email") String correopersonal,

        @NotBlank(message = "Necesita ingresar todos los datos") String direccion,

        @NotNull (message = "Necesita ingresar todos los datos") List<Integer> rolesId,

        @NotNull(message = "Necesitas ingresar todos los datos") List<HorarioDto> horarios

) {
}
