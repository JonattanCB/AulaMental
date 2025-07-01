package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.model.Apoderado;
import org.springframework.stereotype.Component;

@Component
public class ApoderadoMapper {

    public static ApoderadoDto toDto(Apoderado apoderado){
        return new ApoderadoDto(apoderado.getId(), PersonaMapper.toConcatNombre(apoderado.getPersona()), apoderado.getPersona().getTelefono1(),
                apoderado.getPersona().getCorreoPersonal(), apoderado.getPersona().getDireccion(),
                apoderado.getParentesco().toString(), apoderado.getPersona().getTelefono2(), apoderado.getOcupacion());
    }

    private ApoderadoMapper(){}
}
