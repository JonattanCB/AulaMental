package com.abs.aulamental.dto.rol;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record RolDto(
        @NotNull int id,
        @NotBlank String nombre
) {}
