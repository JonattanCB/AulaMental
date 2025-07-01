package com.abs.aulamental.dto.permisos;

import java.util.List;

public record PermisoMenuDto(
        int id,
        String label,
        String icon,
        String url,
        List<PermisoMenuDto> items
) {
}
