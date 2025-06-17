package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.asistencia.AsistenciaDetailDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDto;
import com.abs.aulamental.dto.asistencia.AsistenciaUsuarioListDto;
import com.abs.aulamental.model.Asistencia;
import com.abs.aulamental.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AsistenciaMapper {

    public static AsistenciaDetailDto toDetailDto(Asistencia asistencia){
        return new AsistenciaDetailDto(asistencia.getFecha(), asistencia.getHora(), asistencia.getEstado());
    }

    public static AsistenciaUsuarioListDto tolistUserDto(Usuario usuario){
        return new AsistenciaUsuarioListDto(usuario.getId(), PersonaMapper.toConcatNombre(usuario.getPersona()), usuario.getPersona().getTelefono1(), usuario.getEstado());
    }

    public static AsistenciaDto toDto(Asistencia asistencia){
        return new AsistenciaDto(asistencia.getId(), asistencia.getFecha(),asistencia.getHora(),asistencia.getEstado());
    }

    private AsistenciaMapper(){}
}
