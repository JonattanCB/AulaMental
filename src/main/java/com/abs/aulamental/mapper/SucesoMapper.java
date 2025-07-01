package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.suceso.*;
import com.abs.aulamental.model.Sucesos;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.NivelGravedad;
import com.abs.aulamental.model.itemSucesos;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SucesoMapper {

    public static Sucesos toCreateSuceso(SucesoCreateDto dto, Usuario usuario){
        return new Sucesos(0,usuario, dto.nombre() , dto.detalles() , dto.argurmentosAlumno(), dto.accionesRealizadas(), DateUtil.getTodaySqlDate(),
                DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), null);
    }

    public static SucesoDto toSucesoDto(Sucesos entity){
        return new SucesoDto(entity.getNombre(), entity.getDetalles(), entity.getArgurmentosalumno(), entity.getAccionesrealizadas(), entity.getFecha());
    }

    public static ItemSucesoDto toItemSucesoDto(itemSucesos entity){
        return new ItemSucesoDto(entity.getSucesos().getId(), entity.getSucesos().getNombre(),entity.getSucesos().getFecha(), entity.getNivelGravedad());
    }

    public static SucesoDetailDto toDetailSuceso(Sucesos sucesos, List<SucesoAlumnoDto> sucesoAlumnos, NivelGravedad nivelGravedad){
        return new SucesoDetailDto(sucesos.getId(), PersonaMapper.toConcatNombre(sucesos.getUsuario().getPersona()), sucesos.getNombre(),sucesos.getFecha() ,sucesos.getDetalles(),
                sucesos.getArgurmentosalumno(), sucesos.getAccionesrealizadas(), nivelGravedad, sucesoAlumnos);
    }

    private SucesoMapper(){}
}
