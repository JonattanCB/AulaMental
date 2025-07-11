package com.abs.aulamental.service.asistencia;

import com.abs.aulamental.dto.asistencia.AsistenciaDetailDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDto;
import com.abs.aulamental.dto.asistencia.AsistenciaUsuarioDetailsDto;
import com.abs.aulamental.dto.asistencia.AsistenciaUsuarioListDto;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.AsistenciaMapper;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.model.Asistencia;
import com.abs.aulamental.model.Horario;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.Dias;
import com.abs.aulamental.model.enums.EstadoAsistencia;
import com.abs.aulamental.model.enums.Roles;
import com.abs.aulamental.repository.AsistenciaRepository;
import com.abs.aulamental.repository.HorarioRepository;
import com.abs.aulamental.repository.UsuarioRepository;
import com.abs.aulamental.service.rol.RolService;
import com.abs.aulamental.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AsistenciaService {
    private final AsistenciaRepository asistenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HorarioRepository horarioRepository;
    private final RolService rolService;

    @Transactional
    public AsistenciaDetailDto createAsistencia(int idUser) {
        var usuario = usuarioRepository.searchUsuarioById(idUser);
        validarCondicionalcreate(usuario);

        Horario horario =  horarioRepository.findByUsuarioAndDia( usuario, Dias.fromDayOfWeek(DateUtil.getTodayDayOfWeek()))
                            .orElseThrow(() ->
                                new ValidarExcepciones("No existe horario para hoy"));

        LocalTime horaProgramada = horario.getHora();
        LocalTime horaActual = LocalTime.now();

        long minutosDiferencia = Duration.between(horaProgramada, horaActual).toMinutes();

        EstadoAsistencia estado;

        if (minutosDiferencia <= 15 && minutosDiferencia >= -60) {
            estado = EstadoAsistencia.PRESENTE;
        } else if (minutosDiferencia > 15 && minutosDiferencia <= 30) {
            estado = EstadoAsistencia.TARDE;
        } else {
            estado = EstadoAsistencia.FALTA;
        }

        return AsistenciaMapper.toDetailDto(asistenciaRepository.save(new Asistencia(0,Date.valueOf(LocalDate.now()),horaActual,estado,DateUtil.nowTimestamp(), DateUtil.nowTimestamp(),usuario)));
    }


    public Page<AsistenciaUsuarioListDto> listAsistencias(int idcreador, String nombre, Pageable pageable) {
        Page<Usuario> usuarios;
        if (rolService.hasRole(idcreador, Roles.Psicologia.name())){
            usuarios = usuarioRepository.listUserOptionalNombre(nombre,Roles.Practicante.name(), pageable);
        }else{
            usuarios = usuarioRepository.listUserOptionalNombre(nombre, null,pageable);
        }
        return usuarios.map(usuario -> AsistenciaMapper.tolistUserDto(usuario,getLastAttendanceDate(usuario.getId())));
    }

    public Page<AsistenciaDto> listAsistenciasUser(int idUsuario, LocalDate fecha, Pageable pageable) {
        Page<Asistencia> asistencias = asistenciaRepository.findByUsuarioAndOptionalFecha(idUsuario, fecha, pageable);
        return asistencias.map(AsistenciaMapper::toDto);
    }

    @Transactional
    public AsistenciaUsuarioDetailsDto countStatusUser(int idUsuario){
        var usuario = usuarioRepository.searchUsuarioById(idUsuario);
        String nombre = PersonaMapper.obtenerIniciales(usuario.getPersona());
        long presente = asistenciaRepository.countByUsuarioIdAndEstado(idUsuario,EstadoAsistencia.PRESENTE);
        long tardanza = asistenciaRepository.countByUsuarioIdAndEstado(idUsuario,EstadoAsistencia.TARDE);
        long faltante = asistenciaRepository.countByUsuarioIdAndEstado(idUsuario,EstadoAsistencia.FALTA);

        return new AsistenciaUsuarioDetailsDto(nombre,presente,tardanza,faltante);
    }


    private void validarCondicionalcreate(Usuario usuario){
        if( asistenciaRepository.existsAsistenciaByUsuarioIdAndFecha(usuario.getId(), Date.valueOf(LocalDate.now()))){
            throw new ValidarExcepciones("Ya existe un registro de asistencia para el usuario hoy");
        }

        var horarioOptional = horarioRepository.findByUsuarioAndDia( usuario, Dias.fromDayOfWeek(DateUtil.getTodayDayOfWeek()));

        if(horarioOptional.isEmpty()){
            throw new ValidarExcepciones("No existe un horario para el usuario hoy");
        }

    }

    private String getLastAttendanceDate(int userId) {
        Date fecha = asistenciaRepository.findUltimaFechaByUsuarioIdNative(userId);
        if (fecha == null) {
            return "Sin asistencia";
        }else{
            return fecha.toString();
        }
    }


}
