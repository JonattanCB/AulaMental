package com.abs.aulamental.mapper;

import com.abs.aulamental.dto.horario.HorarioDto;
import com.abs.aulamental.dto.user.*;
import com.abs.aulamental.model.Persona;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public static Usuario toCreateUsuario(UsuarioCreateDto dto){
        var persona = new Persona(0,dto.nombre(),dto.apaterno(),dto.amaterno(),
                dto.tdocumento(),dto.ndocumento(),dto.telefono1(),dto.telefono2(),
                dto.correopersonal(),dto.direccion(),dto.lnacimiento(),dto.fnacimiento(),
                DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), Estado.ACTIVO);

        return new Usuario(0, persona, dto.correo(), dto.contrasenia(), Estado.ACTIVO, DateUtil.nowTimestamp(), DateUtil.nowTimestamp(),null,null,null, null);
    }

    public static UsuarioDetailDto toDetailDto(Usuario usuario, List<String> roles, List<String> horarios){
        return  new UsuarioDetailDto( usuario.getId(), PersonaMapper.toConcatNombre(usuario.getPersona()), usuario.getPersona().getTelefono1(),usuario.getPersona().getTelefono2(),
                usuario.getCorreo(),usuario.getFregistro().toString(),usuario.getEstado(),roles,horarios);
    }

    public static UsuarioDto toDto(Usuario entity){
        List<HorarioDto> horariosDtos = entity.getHorarios().stream().map(horario -> new HorarioDto(horario.getDia(), horario.getHora())).toList();
        List<Integer>  rolactivos = entity.getUsuarioRoles().stream().filter(usuarioRol -> usuarioRol.getEstado() == Estado.ACTIVO)
                .map(usuarioRol -> usuarioRol.getRol().getId()).toList();

        return  new UsuarioDto(entity.getId(), entity.getPersona().getNombre(), entity.getPersona().getApaterno(),
                entity.getPersona().getAmaterno(), entity.getPersona().getTdocumento(), entity.getPersona().getNdocumento(),
                entity.getPersona().getTelefono1(), entity.getPersona().getTelefono2(), entity.getPersona().getCorreoPersonal(),
                entity.getPersona().getDireccion(), rolactivos,horariosDtos);
    }

    public static UsuarioEstadoUpdateDto toUpdateEstadoDto(Usuario entity){
        return new UsuarioEstadoUpdateDto(entity.getId(), PersonaMapper.toConcatNombre(entity.getPersona()), entity.getPersona().getEstado());
    }

    public static UsuarioListDto toListDto(Usuario entity){
        return new UsuarioListDto(entity.getId(), PersonaMapper.toConcatNombre(entity.getPersona()), entity.getCorreo(), entity.getPersona().getTelefono1(), entity.getEstado());
    }



    private UsuarioMapper() {}


}
