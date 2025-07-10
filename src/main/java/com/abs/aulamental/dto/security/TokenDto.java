package com.abs.aulamental.dto.security;

import java.util.List;

public record TokenDto(
        int id,
        String nombre,
        String token,
        String email,
        String alias,
        List<String> roles
) {
}
