package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.Apoderado.ApoderadoDetailsSelectDto;
import com.abs.aulamental.dto.atencionapoderado.*;
import com.abs.aulamental.model.Apoderado;
import com.abs.aulamental.model.AtencionApoderados;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class AtencionApoderadoMapper {
    public static AtencionApoderados tocreateAtenApoderado(AtenApoderadoCreateDto dto, Apoderado apoderado) {
        return new AtencionApoderados(0,apoderado,dto.motivo(), dto.resumen(), dto.conclusiones(), dto.recomendaciones(), dto.intervencion(), dto.fecha(), dto.comentario(), DateUtil.nowTimestamp(), DateUtil.nowTimestamp());
    }

    public static AtenApoderadoDetailDto toDetailAtenApoderado(AtencionApoderados entity){
        return new AtenApoderadoDetailDto(PersonaMapper.toConcatNombre(entity.getApoderado().getPersona()), entity.getMotivo(), entity.getResumen(),
                entity.getResumen(), entity.getConclusiones(), entity.getIntervencion(),entity.getFecha(),entity.getComentario());
    }

    public static AtenApoderadoListDto toListAtenApoderado(Apoderado apoderado, long cantAtencion, String ultimafecha){
        return new AtenApoderadoListDto(apoderado.getId(), PersonaMapper.toConcatNombre(apoderado.getPersona()), apoderado.getPersona().getTdocumento(),
                apoderado.getPersona().getNdocumento(),apoderado.getPersona().getTelefono1(), apoderado.getPersona().getTelefono2(), cantAtencion, ultimafecha );
    }

    public static AtenApoderadoListDetailDto toListAtenApoderadoDetail(AtencionApoderados entity){
        return  new AtenApoderadoListDetailDto(entity.getId(), entity.getMotivo(),entity.getFecha());
    }

    public static AtenApoderadoDto toDto(AtencionApoderados entity){
        return new AtenApoderadoDto(entity.getId(), PersonaMapper.toConcatNombre(entity.getApoderado().getPersona()) , entity.getMotivo(), entity.getResumen(), entity.getResumen(), entity.getConclusiones(), entity.getIntervencion()
                ,entity.getFecha(),entity.getComentario());
    }

    public static ApoderadoDetailsSelectDto toListSelectApoderad(Apoderado apoderado, long cantidad,String ultimaAsistencia ) {
        return new ApoderadoDetailsSelectDto(apoderado.getId(),PersonaMapper.obtenerIniciales(apoderado.getPersona()),
                PersonaMapper.toConcatNombre(apoderado.getPersona()),apoderado.getPersona().getTdocumento(),apoderado.getPersona().getNdocumento(),
                cantidad ,apoderado.getPersona().getTelefono1(), ultimaAsistencia);
    }

    private AtencionApoderadoMapper(){};

}
