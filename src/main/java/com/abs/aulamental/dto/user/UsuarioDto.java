package com.abs.aulamental.dto.user;

import com.abs.aulamental.dto.horario.HorarioUsuarioDto;
import com.abs.aulamental.dto.rol.RolDto;
import com.abs.aulamental.model.enums.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record UsuarioDto(
        @NotNull(message = "Necesita ingresar todos los datos") int id,

        @NotNull(message = "Necesita ingresar todo los datos") String iniciales,

        @NotBlank(message = "Necesita ingresar todos los datos") String nombre,

        @NotBlank(message = "Necesita ingresar todos los datos") String apaterno,

        @NotBlank(message = "Necesita ingresar todos los datos") String amaterno,

        @NotBlank(message = "Necesita ingresar todos los datos") String tdocumento,

        @NotBlank(message = "Necesita ingresar todos los datos") String ndocumento,

        @NotBlank(message = "Necesita ingresar todos los datos") String lnacimiento,

        @NotBlank(message = "Necesita ingresar todos los datos") String fechanacimiento,

        @NotBlank(message = "Necesita ingresar todos los datos") String telefono1,

        String telefono2,

        @NotBlank(message = "Necesita ingresar todos los datos") @Email(message = "No esta en formato email") String correopersonal,

        @NotBlank(message = "Necesita ingresar todos los datos") String direccion,

        @NotBlank(message = "Necesita ingresar todos los datos") String correoinstitucional,

        @NotNull(message = "Necesita ingresar todos los datos") Estado estado,
        
        @NotNull(message = "Se necesita datos") List<RolDto> rolDtos,

        @NotNull(message = "Necesitas ingresar todos los datos") List<HorarioUsuarioDto> horarios
) {
}
