package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.asignar.*;
import com.abs.aulamental.model.Asignar;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class AsignarMappper {
    public static Asignar toCreateAsignar(AsignarCreateDto dto, Usuario psicologo, Usuario practicante){
        return new Asignar(0,psicologo,practicante,dto.tdocumento(),dto.iddocumento(),dto.estadoDocumento(),dto.observacion(), DateUtil.getTodaySqlDate());
    }

    public static AsignarDetailsDto toAsignarDetailsDto(Asignar asignar){
        return new AsignarDetailsDto(PersonaMapper.toConcatNombre(asignar.getUsuario().getPersona()), PersonaMapper.toConcatNombre(asignar.getPracticante().getPersona()),asignar.getTdocumento(),
                asignar.getIdDocumento(), asignar.getEstado(),asignar.getObservaciones());
    }

    public static AsignarListPracticantesDto toAsignarListPracticantesDto(Asignar entity){
        return new AsignarListPracticantesDto(entity.getId(), entity.getTdocumento(),entity.getEstado() ,entity.getFechaCreacion(),entity.getIdDocumento());
    }

    public static AsignarListTablePracticanteDto toAsignarListTablePracticantesDto(Usuario usuario, String fecha, Long pendiente, Long enviado){
        return new AsignarListTablePracticanteDto(usuario.getId(), PersonaMapper.toConcatNombre(usuario.getPersona()),usuario.getPersona().getTelefono1(),fecha,pendiente, enviado);
    }

    public static AsignarTaskListDto toAsignarTaskList(Asignar entity, String nombre){
        return new AsignarTaskListDto(entity.getId(), nombre, entity.getTdocumento(),entity.getEstado() ,entity.getFechaCreacion(), entity.getIdDocumento(),entity.getObservaciones());
    }

    public static String titulo(Tipodocumentacion tipodocumentacion){
        if (tipodocumentacion==Tipodocumentacion.ATENCIONALUMNO){
            return "Atencion Alumno";
        }

        if (tipodocumentacion==Tipodocumentacion.ATENCIONAPODERADO){
            return "Atencion Apoderado";
        }
        return "";
    }

    private AsignarMappper(){}
}
