package com.abs.aulamental.mapper;


import com.abs.aulamental.dto.cita.CitaAlumnoListDto;
import com.abs.aulamental.dto.cita.CitaDetailsDto;
import com.abs.aulamental.dto.cita.CitaPsicologoListDto;
import com.abs.aulamental.model.Cita;

public class CitaMapper {

    public static CitaDetailsDto toDetails(Cita entity){
        return  new CitaDetailsDto(entity.getId(), entity.getFecha(), entity.getHora(),
                entity.getAlumno().getId(), entity.getPsicologo().getId(), entity.getMotivo(), entity.getEstado().toString(), entity.getObservaciones());
    }

    public static CitaAlumnoListDto toListAlumnoDto(Cita entity) {
        return new CitaAlumnoListDto(PersonaMapper.toConcatNombre(entity.getPsicologo().getPersona()), entity.getFecha().toString(), entity.getHora().toString(),
               entity.getEstado().toString());
    }

    public static CitaPsicologoListDto toListPsicologoDto(Cita entity, String numero) {
        return new CitaPsicologoListDto(entity.getId(),PersonaMapper.toConcatNombre(entity.getAlumno().getPersona()),numero, entity.getFecha().toString(), entity.getHora().toString(),
                entity.getEstado().toString(), entity.getMotivo());
    }

    private CitaMapper() {}

}