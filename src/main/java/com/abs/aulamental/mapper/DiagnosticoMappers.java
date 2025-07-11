package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.diagnostico.*;
import com.abs.aulamental.model.Diagnostico;
import com.abs.aulamental.model.enums.Estado;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticoMappers {

    public static DiagnosticoListDto toListDiagnotico(Diagnostico entity){
       return new DiagnosticoListDto(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getEstado());
    }

    public static DiagnosticoListSelectDto toListDiagnosticoSelec(Diagnostico entity){
        return new DiagnosticoListSelectDto(entity.getId(),entity.getNombre(),entity.getDescripcion());
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
