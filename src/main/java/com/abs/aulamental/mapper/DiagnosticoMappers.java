package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.diagnostico.DiagnosticoCreateDto;
import com.abs.aulamental.dto.diagnostico.DiagnosticoDetailDto;
import com.abs.aulamental.dto.diagnostico.DiagnosticoDto;
import com.abs.aulamental.dto.diagnostico.DiagnosticoListDto;
import com.abs.aulamental.model.Diagnostico;
import com.abs.aulamental.model.enums.Estado;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticoMappers {

    public static DiagnosticoListDto toListDiagnotico(Diagnostico entity){
       return new DiagnosticoListDto(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getEstado());
    }

    public static  Diagnostico toCreateDiagnostico(DiagnosticoCreateDto dto){
        return  new Diagnostico(0, dto.nombre(),dto.descripcion(), Estado.ACTIVO);
    }

    public static DiagnosticoDetailDto toDetailDiagnostico(Diagnostico entity){
        return new DiagnosticoDetailDto(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getEstado());
    }

    public static DiagnosticoDto toDto(Diagnostico entity){
        return new DiagnosticoDto(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getEstado());
    }


    private DiagnosticoMappers(){}
}
