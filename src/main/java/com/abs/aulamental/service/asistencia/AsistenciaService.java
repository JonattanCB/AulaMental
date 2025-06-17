package com.abs.aulamental.service.asistencia;

import com.abs.aulamental.dto.asistencia.AsistenciaDetailDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDto;
import com.abs.aulamental.dto.asistencia.AsistenciaUsuarioListDto;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.AsistenciaMapper;
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
        return usuarios.map(AsistenciaMapper::tolistUserDto);
    }

    public Page<AsistenciaDto> listAsistenciasUser(int idUsuario, Date fecha, Pageable pageable) {
        Page<Asistencia> asistencias = asistenciaRepository.findByUsuarioAndOptionalFecha(idUsuario, fecha, pageable);
        return asistencias.map(AsistenciaMapper::toDto);
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



}
