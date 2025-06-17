package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.rol.RolDto;
import com.abs.aulamental.model.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public static RolDto toDto(Rol rol) {
        return new RolDto(rol.getId(), rol.getNombre());
    }

    private RolMapper(){}
}
