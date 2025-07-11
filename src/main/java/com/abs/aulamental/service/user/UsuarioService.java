package com.abs.aulamental.service.user;

import com.abs.aulamental.dto.horario.HorarioDto;
import com.abs.aulamental.dto.permisos.PermisoMenuDto;
import com.abs.aulamental.dto.security.CambioContraseniaCodeDto;
import com.abs.aulamental.dto.security.CodeGeneradorDto;
import com.abs.aulamental.dto.security.RespuestaCodeDto;
import com.abs.aulamental.dto.security.validacionCodeDto;
import com.abs.aulamental.dto.user.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.HorarioMapper;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.mapper.UsuarioMapper;
import com.abs.aulamental.model.Permiso;
import com.abs.aulamental.model.Rol;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.UsuarioRol;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.model.enums.Roles;
import com.abs.aulamental.repository.*;
import com.abs.aulamental.service.horario.HorarioService;
import com.abs.aulamental.service.rol.RolService;
import com.abs.aulamental.utils.DateUtil;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonasRepository personasRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final HorarioRepository horarioRepository;
    private final RolService rolService;
    private final HorarioService horarioService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

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

    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
    public String updatePassword(int id, @NotNull @Valid UsuarioContraseñaDto dto) {
        Usuario usuario = usuarioRepository.searchUsuarioById(id);

        if (!passwordEncoder.matches(dto.contraseñaActual(), usuario.getContrasena())) {
            throw new ValidarExcepciones("La contraseña actual es incorrecta");
        }

        if (!dto.nuevaContraseña().equals(dto.confirmarContraseña())) {
            throw new ValidarExcepciones("La nueva contraseña y la confirmación no coinciden");
        }
        usuario.actualizarContrasena(passwordEncoder.encode(dto.nuevaContraseña()));
        return "Contraseña actualizada correctamente";
    }

    @Transactional(readOnly = true)
    public List<Permiso> listarPermisosPorUsuario(int idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Set<Integer> idsRoles = usuario.getUsuarioRoles().stream()
                .filter(ur -> ur.getEstado() == Estado.ACTIVO)
                .map(ur -> ur.getRol().getId())
                .collect(Collectors.toSet());

        if (idsRoles.isEmpty()) {
            return List.of();
        }

        List<Permiso> permisos = permisoRepository.findByRoles(new ArrayList<>(idsRoles));

        Map<String, Permiso> unicos = new LinkedHashMap<>();
        for (Permiso permiso : permisos) {
            if (permiso.getParentId() == 0) {
                // Siempre añadimos cabeceras
                unicos.put("HEADER-" + permiso.getId(), permiso);
            } else if (permiso.getUrl() != null) {
                // Eliminamos duplicados de submenús por URL
                unicos.putIfAbsent("ITEM-" + permiso.getUrl(), permiso);
            }
        }

        return new ArrayList<>(unicos.values());
    }

    @Transactional(readOnly = true)
    public List<PermisoMenuDto> construirMenuParaUsuario(int idUsuario) {
        List<Permiso> permisos = listarPermisosPorUsuario(idUsuario);

        if (permisos.isEmpty()) {
            return List.of();
        }

        Map<Integer, List<Permiso>> agrupadosPorPadre = permisos.stream()
                .collect(Collectors.groupingBy(Permiso::getParentId));

        List<Permiso> padres = agrupadosPorPadre.getOrDefault(0, List.of());

        if (padres.isEmpty()) {
            return permisos.stream()
                    .filter(p -> p.getParentId() != 0)
                    .map(p -> new PermisoMenuDto(
                            p.getId(),
                            p.getLabel(),
                            p.getIcon(),
                            p.getUrl(),
                            null // No hijos
                    ))
                    .collect(Collectors.toList());
        }

        // Menú normal con cabeceras
        return padres.stream()
                .map(padre -> toDtoConHijos(padre, agrupadosPorPadre))
                .collect(Collectors.toList());
    }

    private PermisoMenuDto toDtoConHijos(Permiso permiso, Map<Integer, List<Permiso>> agrupadosPorPadre) {
        List<Permiso> hijos = agrupadosPorPadre.getOrDefault(permiso.getId(), List.of());

        List<PermisoMenuDto> items = hijos.stream()
                .map(hijo -> toDtoConHijos(hijo, agrupadosPorPadre))
                .collect(Collectors.toList());

        return new PermisoMenuDto(
                permiso.getId(),
                permiso.getLabel(),
                permiso.getIcon(),
                permiso.getUrl(),
                items.isEmpty() ? null : items
        );
    }

    @Transactional
    public CodeGeneradorDto generedorCode(String correo) {
        if (!usuarioRepository.existsByCorreo(correo)){
            throw new ValidarExcepciones("El correo no existe en el sistema");
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo);
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * caracteres.length());
            codigo.append(caracteres.charAt(index));
        }

        try{
            String nombre = PersonaMapper.toConcatNombre(usuario.getPersona());
            String contenidoHtml = """
                <div style="font-family: Arial, sans-serif; padding: 20px; background: #f9f9f9; border-radius: 8px;">
                    <h2 style="color: #2c3e50;">¡Hola, %s!</h2>
                    <p>Tu código de verificación es:</p>
                    <div style="font-size: 28px; font-weight: bold; color: #2980b9; margin: 20px 0;">%s</div>
                    <p>Por favor, usa este código para continuar con tu proceso.</p>
                    <p style="color: #7f8c8d;">Este código expirará en los próximos minutos.</p>
                    <br>
                    <small style="color: #bdc3c7;">No compartas este código con nadie.</small>
                </div>
            """.formatted(nombre, codigo.toString());

            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(usuario.getPersona().getCorreoPersonal());
            helper.setSubject("Código de Verificación");
            helper.setText(contenidoHtml, true);
            helper.setFrom("sebastiancb233@gmail.com");
            mailSender.send(mensaje);

            usuario.actualizarcodigoExpiracion(LocalDateTime.now().plusMinutes(10), codigo.toString());

        }catch (Exception e){
            throw new ValidarExcepciones("Error al mandar el codigo");
        }
        return new CodeGeneradorDto(codigo.toString());
    }

    @Transactional
    public RespuestaCodeDto validacionCode(validacionCodeDto dto) {
        Usuario usuario = usuarioRepository.findByCorreo(dto.correo());
        if (usuario == null) {
            throw new ValidarExcepciones("Correo no existe.");
        }

        if (usuario.getCodigoverificacion() == null || usuario.getCodigoexpiracion() == null) {
            throw new ValidarExcepciones("No se ha generado un código.");
        }

        if (LocalDateTime.now().isAfter(usuario.getCodigoexpiracion())) {
            throw new ValidarExcepciones("El código ha expirado.");
        }

        if (!usuario.getCodigoverificacion().equalsIgnoreCase(dto.code())) {
            throw new ValidarExcepciones("El código no es válido.");
        }

        return new RespuestaCodeDto("Codigo Valido");
    }

    @Transactional
    public RespuestaCodeDto changerPasswordCode(CambioContraseniaCodeDto dto) {
        if (!dto.nuevaContrasena().equals(dto.repetirContrasena())) {
            throw new ValidarExcepciones("Las contraseñas no coinciden.");
        }

        Usuario usuario = usuarioRepository.findByCorreo(dto.correo());
        if (usuario == null) {
            throw new ValidarExcepciones("Correo no existe.");
        }

        if (usuario.getCodigoverificacion() == null || usuario.getCodigoexpiracion() == null) {
            throw new ValidarExcepciones("No se ha generado un código.");
        }

        if (LocalDateTime.now().isAfter(usuario.getCodigoexpiracion())) {
            throw new ValidarExcepciones("El código ha expirado.");
        }

        if (!usuario.getCodigoverificacion().equalsIgnoreCase(dto.codigo())) {
            throw new ValidarExcepciones("El código no es válido.");
        }

        usuario.actualizarContrasena(passwordEncoder.encode(dto.nuevaContrasena()));
        usuario.actualizarcodigoExpiracion(null, null);

        return new RespuestaCodeDto("Contraseña actualizada correctamente.");
    }
}
