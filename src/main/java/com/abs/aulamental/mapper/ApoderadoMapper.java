package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.Apoderado.ApoderadoCreateDto;
import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.Apoderado;
import com.abs.aulamental.model.Persona;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class ApoderadoMapper {

    public static ApoderadoDto toDto(Apoderado apoderado){
        return new ApoderadoDto(apoderado.getId(), PersonaMapper.toConcatNombre(apoderado.getPersona()), apoderado.getPersona().getTelefono1(),
                apoderado.getPersona().getCorreoPersonal(), apoderado.getPersona().getDireccion(),
                apoderado.getParentesco().toString(), apoderado.getPersona().getTelefono2(), apoderado.getOcupacion());
    }

    public static Apoderado toCreateApoderado(ApoderadoCreateDto dto, Alumno alumno){
        var persona = new Persona(0,dto.nombre(),dto.apaterno(),dto.amaterno(),
                dto.tdocumento(),dto.ndocumento(),dto.telefono1(),dto.telefono2(),
                dto.correopersonal(),dto.direccion(),dto.lnacimiento(),dto.fnacimiento(),
                DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), Estado.ACTIVO);
        return new Apoderado(0,persona,alumno,DateUtil.nowTimestamp(), DateUtil.nowTimestamp(),Estado.ACTIVO,dto.ocupacion(),dto.parentesco(),dto.convive());
    }

    private ApoderadoMapper(){}
}
