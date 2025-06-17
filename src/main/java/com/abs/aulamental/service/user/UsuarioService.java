package com.abs.aulamental.service.user;

import com.abs.aulamental.dto.horario.HorarioDto;
import com.abs.aulamental.dto.user.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.HorarioMapper;
import com.abs.aulamental.mapper.UsuarioMapper;
import com.abs.aulamental.model.Rol;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.UsuarioRol;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.model.enums.Roles;
import com.abs.aulamental.repository.*;
import com.abs.aulamental.service.horario.HorarioService;
import com.abs.aulamental.service.rol.RolService;
import com.abs.aulamental.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonasRepository personasRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final HorarioRepository horarioRepository;
    private final RolService rolService;
    private final HorarioService horarioService;
    private final PasswordEncoder passwordEncoder;

    public Page<UsuarioListDto> listUser(int idcreador, String nombre,  Pageable pageable) {
        Page<Usuario> usuarios;
        if (rolService.hasRole(idcreador, Roles.Psicologia.name())){
           usuarios = usuarioRepository.listUserOptionalNombre(nombre, Roles.Practicante.name(),pageable );
        }else{
            usuarios =  usuarioRepository.listUserOptionalNombre(nombre, null,pageable );
        }

        return usuarios.map(UsuarioMapper::toListDto);
    }

    public UsuarioDto getUser(int id){
        return UsuarioMapper.toDto(usuarioRepository.searchUsuarioById(id));
    }

    public UsuarioDetailDto createUser( UsuarioCreateDto dto) {

        validarCondicionalescreate(dto);

        var entity = UsuarioMapper.toCreateUsuario(dto);
        entity.setContrasena(passwordEncoder.encode(entity.getContrasena()));
        entity.setPersona(personasRepository.save(entity.getPersona()));

        var usuario = usuarioRepository.save(entity);

        List<String> rspRoles;

        if (rolService.hasRole(dto.idcreador(), Roles.Psicologia.name())){
            rspRoles = asignarUsuarioRolPracticante(usuario);
        }else{
            rspRoles = asignarUsuarioRol(dto.idRoles(), usuario);
        }

        var rspHorario = asignarHorarioUsuario(dto.horarios(), usuario);

        return UsuarioMapper.toDetailDto(usuario,rspRoles,rspHorario);
    }

    public UsuarioDetailDto updateUser(UsuarioUpdateDto dto) {
        validarCondicionalesupdate();

        Usuario usuario = usuarioRepository.searchUsuarioById(dto.id());

        usuario.getPersona().updatePersona(dto);

        List<String> rspRoles;

        if (rolService.hasRole(dto.idcreador(), Roles.Psicologia.name())){
            rspRoles = usuario.getUsuarioRoles().stream().map(usuarioRol ->  usuarioRol.getRol().getNombre()).collect(Collectors.toList());
        }else{
            rspRoles = modificarAsignacionRoles(usuario, dto.rolesId());
        }

        List<String> rspHorario = modificarHorarioUsuario(dto.horarios(), dto.id());

        return UsuarioMapper.toDetailDto(usuario,rspRoles,rspHorario);
    }

    public UsuarioEstadoUpdateDto changerEstado(int id){
        var usuario = usuarioRepository.searchUsuarioById(id);
        if (usuario.getEstado().equals(Estado.ACTIVO)){
            usuario.actualizarEstado(Estado.INACTIVO);
        }else{
            usuario.actualizarEstado(Estado.ACTIVO);
        }
        return UsuarioMapper.toUpdateEstadoDto(usuario);
    }

    private void validarCondicionalesupdate(){
        if (!rolRepository.existsByEstado(Estado.ACTIVO)) {
            throw new ValidarExcepciones("No hay roles disponibles en el sistema.");
        }
    }

    private void  validarCondicionalescreate(UsuarioCreateDto dto) {
        if (usuarioRepository.existsByCorreo(dto.correo())){
            throw new ValidarExcepciones("El correo ya está registrado.");
        }

        if (personasRepository.existsPersonaByNdocumento(dto.ndocumento())) {
            throw new ValidarExcepciones("La persona ya está registrada en el sistema.");
        }

        if (!rolRepository.existsByEstado(Estado.ACTIVO)) {
            throw new ValidarExcepciones("No hay roles disponibles en el sistema.");
        }
    }

    private List<String> asignarUsuarioRolPracticante(Usuario usuario){
        List<String> rolesAsignados = new ArrayList<>();
        Rol rol = rolRepository.getRolByNombre(Roles.Practicante.name());
        if (usuarioRolRepository.existsUsuarioRolByIdUsuarioAndIdRol(usuario.getId(), rol.getId())) {
            throw new ValidarExcepciones("El rol con ID " + rol.getId() + " ya está asignado al usuario.");
        } else {
            var rolAsignado = rolRepository.getById(rol.getId());
            usuarioRolRepository.save(new UsuarioRol(0, usuario, rolAsignado, DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), Estado.ACTIVO));
            rolesAsignados.add(rolAsignado.getNombre());
        }
        return rolesAsignados;
    }

    private List<String> asignarUsuarioRol(List<Integer> idRoles, Usuario usuario) {
        List<String> rolesAsignados = new ArrayList<>();

        for (Integer idRol : idRoles) {
            if (usuarioRolRepository.existsUsuarioRolByIdUsuarioAndIdRol(usuario.getId(), idRol)) {
                throw new ValidarExcepciones("El rol con ID " + idRol + " ya está asignado al usuario.");
            } else {
                var rolAsignado = rolRepository.getById(idRol);
                usuarioRolRepository.save(new UsuarioRol(0, usuario, rolAsignado, DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), Estado.ACTIVO));
                rolesAsignados.add(rolAsignado.getNombre());
            }
        }

        return rolesAsignados;
    }

    private List<String> asignarHorarioUsuario( List<HorarioDto> horarios, Usuario usuario) {
        List<String> horariosAsignados = new ArrayList<>();

        for (HorarioDto horario : horarios) {
            var nuevoHorario = horarioRepository.save(HorarioMapper.toCreateHorario(horario,usuario));
            horariosAsignados.add(nuevoHorario.getDia() +" - "+nuevoHorario.getHora());
        }

        return horariosAsignados;
    }

    private List<String> modificarAsignacionRoles(Usuario usuario, List<Integer> idRolesNuevos) {
        List<String > rolesNuevos = new ArrayList<>();

        List<UsuarioRol> usuarioRoles = usuario.getUsuarioRoles();

        Set<Integer> idsRolesAntiguosActivos = usuarioRoles.stream()
                .filter(usuarioRol -> usuarioRol.getEstado() == Estado.ACTIVO)
                .map(usuarioRol -> usuarioRol.getRol().getId())
                .collect(Collectors.toSet());

        Set<Integer> idsRolesAntiguos = usuarioRoles.stream()
                .map(usuarioRol -> usuarioRol.getRol().getId())
                .collect(Collectors.toSet());

        Set<Integer> idsRolesNuevos = new HashSet<>(idRolesNuevos);

        if (idsRolesAntiguosActivos.equals(idsRolesNuevos)) {
            return usuario.getUsuarioRoles().stream().filter(usuarioRol -> usuarioRol.getEstado() == Estado.ACTIVO)
                    .map(usuarioRol -> usuarioRol.getRol().getNombre())
                    .toList();
        }

        for (UsuarioRol usuarioRol : usuarioRoles) {
            int idRol = usuarioRol.getRol().getId();
            if (idsRolesNuevos.contains(idRol) && usuarioRol.getEstado() == Estado.INACTIVO) {
                rolService.changeUserRoleStatus(usuario.getId(), idRol, Estado.ACTIVO);
                rolesNuevos.add(usuarioRol.getRol().getNombre());
            }
        }

        for (int idRolNuevo : idsRolesNuevos) {
            if (!idsRolesAntiguos.contains(idRolNuevo)) {
                var rolAsignado = rolRepository.getById(idRolNuevo);
                rolService.createUserRol(new UsuarioRol(0, usuario, rolAsignado, DateUtil.nowTimestamp(), DateUtil.nowTimestamp(), Estado.ACTIVO));
                rolesNuevos.add(rolAsignado.getNombre());
            }
        }

        for (UsuarioRol usuarioRol : usuarioRoles) {
            int idRol = usuarioRol.getRol().getId();
            if (!idsRolesNuevos.contains(idRol) && usuarioRol.getEstado() == Estado.ACTIVO) {
                rolService.changeUserRoleStatus(usuario.getId(), idRol, Estado.INACTIVO);
            }
        }
        Usuario user = usuarioRepository.searchUsuarioById(usuario.getId());
        return user.getUsuarioRoles().stream().filter(usuarioRol -> usuarioRol.getEstado() == Estado.ACTIVO)
                .map(usuarioRol -> usuarioRol.getRol().getNombre())
                .toList();
    }

    private List<String>  modificarHorarioUsuario(List<HorarioDto> horarios, int idUsuario){
        List<String> horariosNuevos = new ArrayList<>();
        var usuario = usuarioRepository.searchUsuarioById(idUsuario);

        Set<HorarioDto> horarioActual = usuario.getHorarios().stream()
                .map(horario -> new HorarioDto(horario.getDia(), horario.getHora()))
                .collect(Collectors.toSet());

        Set<HorarioDto> horarioNuevo = new HashSet<>(horarios);


        if (horarioActual.equals(horarioNuevo)) {
            horariosNuevos = usuario.getHorarios().stream().map(horario -> horario.getDia() +" - "+ horario.getHora()).toList();
            return horariosNuevos ;
        }

        var eliminacionHorarios = horarioService.deleteScheduleByUserId(idUsuario);

        if (eliminacionHorarios){
            for (HorarioDto horario : horarios) {
                var nuevoHorario = HorarioMapper.toCreateHorario(horario,usuario);
                horarioRepository.save(nuevoHorario);
                horariosNuevos.add(nuevoHorario.getDia() +" - "+nuevoHorario.getHora());
            }
        }

        return horariosNuevos;
    }

}
