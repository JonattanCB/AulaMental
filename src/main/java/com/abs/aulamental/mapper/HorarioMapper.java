package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.horario.HorarioDto;
import com.abs.aulamental.model.Horario;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper {
    public static Horario toCreateHorario(HorarioDto dto, Usuario usuario) {
        return new Horario(0, dto.dias(), dto.hora(), DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), usuario);
    }

    public static String obtenerDiminutivo(String dia) {
        return dia.substring(0, 3);
    }
    private HorarioMapper() {}

}
