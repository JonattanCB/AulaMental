package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.dto.alumno.AlumnoDto;
import com.abs.aulamental.dto.alumno.AlumnoListDto;
import com.abs.aulamental.dto.alumno.AlumnoSucesosListDto;
import com.abs.aulamental.dto.alumno.CreateAlumnoDto;
import com.abs.aulamental.dto.suceso.SucesoAlumnoListDto;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.Persona;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AlumnoMapper {

    public static Alumno tocreateDto(CreateAlumnoDto dto){
        var persona = new Persona(0,dto.nombre(),dto.apaterno(),dto.amaterno(),
                dto.tdocumento(),dto.ndocumento(),dto.telefono1(),dto.telefono2(),
                dto.correopersonal(),dto.direccion(),dto.lnacimiento(),dto.fnacimiento(),
                DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), Estado.ACTIVO);
        return new Alumno(0,persona,dto.nivel(),dto.grado(),DateUtil.nowTimestamp(), DateUtil.nowTimestamp(),Estado.ACTIVO,null,null, null);
    }

    public static SucesoAlumnoListDto tolistSucesoAlumno(Alumno alumno, String contact1, String contact2){
        return new SucesoAlumnoListDto(alumno.getId(), PersonaMapper.toConcatNombre(alumno.getPersona()),toConcatNivelAlumno(alumno), contact1,contact2);
    }

    public  static AlumnoDto toDto(Alumno alumno, List<ApoderadoDto> apoderadoDtos){
        int edad = DateUtil.calculateAge(LocalDate.parse(alumno.getPersona().getFnacimiento()));
        return  new AlumnoDto(alumno.getId(), PersonaMapper.toConcatNombre(alumno.getPersona()), toConcatNivelAlumno(alumno),
                edad, alumno.getPersona().getTelefono1(), alumno.getPersona().getDireccion(), alumno.getGrado(), alumno.getNivel(), apoderadoDtos);
    }

    public static AlumnoSucesosListDto tolistAlumnoitemSuceso(Alumno alumno){
        return new AlumnoSucesosListDto(alumno.getId(),PersonaMapper.toConcatNombre(alumno.getPersona()),toConcatNivelAlumno(alumno));
    }

    public static AlumnoListDto tolistAlumno(Alumno alumno, String telefono){
        int edad = DateUtil.calculateAge(LocalDate.parse(alumno.getPersona().getFnacimiento()));
        return  new AlumnoListDto(alumno.getId(), PersonaMapper.toConcatNombre(alumno.getPersona()), toConcatNivelAlumno(alumno),
              edad, telefono, alumno.getPersona().getDireccion());
    }

    public static  String toConcatNivelAlumno(Alumno entity) {
        return Stream.of(entity.getGrado(),PersonaMapper.toTitleCase(entity.getNivel().toString()))
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(s -> !s.trim().isEmpty())
                .collect(Collectors.joining(" de "));
    }

    private AlumnoMapper(){}
}
