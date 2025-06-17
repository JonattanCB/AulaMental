package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.atencionalumno.*;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.AtencionAlumno;
import com.abs.aulamental.model.Diagnostico;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class AtencionAlumnoMapper {

    public static AtencionAlumno toCreateAtenAlumnoPsicologo(AtenAlumnoCreateDto dto, Alumno alumno, Diagnostico diagnostico){
        return new AtencionAlumno(0, alumno, dto.motivo(), dto.resumen(), dto.conclusion(), dto.recomendacion(), dto.tecnicas(),
                diagnostico, dto.comentario() , DateUtil.getTodaySqlDate(), DateUtil.nowTimestamp(), DateUtil.nowTimestamp());
    }

    public static AtenAlumnoDetailDto toDetailAtenAlumno(AtencionAlumno entity){
        return new AtenAlumnoDetailDto(PersonaMapper.toConcatNombre(entity.getAlumno().getPersona()), entity.getMotivo(),entity.getResumen(), entity.getConclusion(), entity.getRecomendacion(),
                entity.getTecnicas(),entity.getComentario(),entity.getDiagnostico().getNombre());
    }

    public static AtenAlumnoListDto toListAtenAlumno(Alumno alumno, String contact1, String contact2){
        return new AtenAlumnoListDto(alumno.getId(), PersonaMapper.toConcatNombre(alumno.getPersona()),AlumnoMapper.toConcatNivelAlumno(alumno), contact1,contact2);
    }

    public static AtenAlumnoDetailListDto toListAtenAlumnoDetalis(AtencionAlumno entity){
        return new AtenAlumnoDetailListDto(entity.getId(),entity.getFecha(), entity.getMotivo());
    }

    public static AtenAlumnoDto toDto(AtencionAlumno entity){
        return new AtenAlumnoDto(entity.getId(),entity.getMotivo(), entity.getResumen(),entity.getConclusion(),entity.getRecomendacion(),entity.getTecnicas()
        , entity.getComentario(), entity.getDiagnostico().getNombre());
    }

    private AtencionAlumnoMapper(){}
}
