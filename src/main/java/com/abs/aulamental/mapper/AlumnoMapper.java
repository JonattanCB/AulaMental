package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.suceso.SucesoAlumnoListDto;
import com.abs.aulamental.model.Alumno;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AlumnoMapper {

    public static SucesoAlumnoListDto tolistSucesoAlumno(Alumno alumno, String contact1, String contact2){
        return new SucesoAlumnoListDto(alumno.getId(), PersonaMapper.toConcatNombre(alumno.getPersona()),toConcatNivelAlumno(alumno), contact1,contact2);
    }

    public static  String toConcatNivelAlumno(Alumno entity) {
        return Stream.of(entity.getGrado(),entity.getNivel())
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(s -> !s.trim().isEmpty())
                .collect(Collectors.joining(" de "));
    }

    private AlumnoMapper(){}
}
