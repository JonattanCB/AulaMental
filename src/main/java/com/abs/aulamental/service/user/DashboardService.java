package com.abs.aulamental.service.user;

import com.abs.aulamental.dto.asignar.TareasDasboardDto;
import com.abs.aulamental.dto.asignar.TareasDasrboardPracticanteDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDasboardDto;
import com.abs.aulamental.dto.dashboard.*;
import com.abs.aulamental.dto.diagnostico.DashboardDiagnosticoListDto;
import com.abs.aulamental.dto.suceso.SucesosDasboardItemDetails;
import com.abs.aulamental.mapper.AsignarMappper;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.model.enums.Dias;
import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.NivelGravedad;
import com.abs.aulamental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AsignarRepository asignarRepository;
    private final SucesoRepository sucesoRepository;
    private final ItemSucesoRepository itemSucesoRepository;
    private final HorarioRepository horarioRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final AtencionAlumnoRepository atencionAlumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlumnoRepository alumnoRepository;

    public DashboardPsicologoDto getDasboardPsicologo(int id) {
        LocalDate fechaInicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        Long totalAtencionMes =  asignarRepository.contarEstadoYPsicologoEnMesActual(id,EstadoDocumento.CERRADO, fechaInicio,fechaFin) ;
        Long totalAtencion = asignarRepository.contarEstadoYPsicologoTotal(id, EstadoDocumento.CERRADO);

        Long totalTareasPendiente= asignarRepository.contarPorEstadoYPsicologo(id, EstadoDocumento.PENDIENTE) ;
        Long totalTareas = asignarRepository.contarAsignacionesPorPsicologo(id);

        Dias diaActual = Dias.fromDayOfWeek(LocalDate.now().getDayOfWeek());

        Long asistenciahoy = horarioRepository.contarUsuariosPracticantesPorDia(diaActual);

        Long asistenciahoyActual = asistenciaRepository.contarAsistenciasPracticantesHoy();

        List<AsistenciaDasboardDto> asistencias = asistenciaRepository.listarAsistenciasHoyDePracticantes().stream().map(
                asistencia -> {
                    return new AsistenciaDasboardDto(asistencia.getUsuario().getId(), PersonaMapper.toConcatNombre(asistencia.getUsuario().getPersona()),asistencia.getHora().toString(),asistencia.getEstado());
                }
        ).toList();

        List<TareasDasboardDto> tareas = asignarRepository.listarTareasEnviadasDePsicologos().stream().map(asignar -> {
            return new TareasDasboardDto(PersonaMapper.toConcatNombre(asignar.getPracticante().getPersona()), asignar.getTdocumento(),asignar.getFechaCreacion().toString());
        }).toList() ;
        List<DashboardDiagnosticoListDto> diagnosticos = atencionAlumnoRepository.obtenerTop5DiagnosticosUsados().stream().map(objects -> new DashboardDiagnosticoListDto(
                (String)  objects[0], ((Number) objects[1]).longValue()
        )).toList() ;

        return new DashboardPsicologoDto(totalAtencionMes,totalAtencion,totalTareasPendiente, totalTareas,asistenciahoy,asistenciahoyActual,asistencias, tareas,diagnosticos);
    }

    public DashboardPracticanteDto getDasboardPracticante(int id) {
        Long perdientes = asignarRepository.contarPorEstadoYPracticante(id,EstadoDocumento.PENDIENTE);
        Long cerrados = asignarRepository.contarPorEstadoYPracticante(id,EstadoDocumento.CERRADO);
        Long revisados = asignarRepository.contarPorEstadoYPracticante(id,EstadoDocumento.REVISADO);
        Long enviados = asignarRepository.contarPorEstadoYPracticante(id,EstadoDocumento.ENVIADO);
        List<TareasDasrboardPracticanteDto> tareas = asignarRepository.listarPorEstadosYPracticante(id, EstadoDocumento.PENDIENTE,EstadoDocumento.REVISADO).stream().map(
                asignar -> {
                    String titulo = "Informe de " + AsignarMappper.titulo(asignar.getTdocumento());
                    return  new TareasDasrboardPracticanteDto(asignar.getId(),titulo,asignar.getFechaCreacion().toString(), asignar.getEstado());
                }
        ).toList();

        return new DashboardPracticanteDto(perdientes,cerrados,revisados,enviados,tareas);
    }

    public DashboardDirectoraDto getDasboardDirectora(int id) {
        Long cantiUsuario = usuarioRepository.contarUsuarios();
        Long cantUsuarioActivos = usuarioRepository.contarUsuariosActivos();

        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(DayOfWeek.MONDAY);
        LocalDate finSemana = hoy.with(DayOfWeek.SUNDAY);
        Timestamp fechaInicioSemana = Timestamp.valueOf(inicioSemana.atStartOfDay());
        Timestamp fechaFinSemana = Timestamp.valueOf(finSemana.atTime(23, 59, 59));

        Long totalSucesos = sucesoRepository.contarSucesosPorUsuarioEnSemanaActual(fechaInicioSemana,fechaFinSemana);
        Long totalAlumnosSucesos = sucesoRepository.contarAlumnosConSucesos();

        Dias diaActual = Dias.fromDayOfWeek(LocalDate.now().getDayOfWeek());
        Long totalAsistenciahoy = horarioRepository.contarUsuariosPorDia(diaActual);
        Long totalAsistenciaActual = asistenciaRepository.contarTotalAsistenciasHoy();
        List<AsistenciaDasboardDto> asistencias = asistenciaRepository.listarAsistenciasHoy().stream().map(asistencia -> {
            return new AsistenciaDasboardDto(asistencia.getUsuario().getId(), PersonaMapper.toConcatNombre(asistencia.getUsuario().getPersona()),asistencia.getHora().toString(),asistencia.getEstado());
        }).toList();
        List<SucesosDasboardItemDetails> sucesos = itemSucesoRepository.listarItemSucesosDelMesActual().stream().map(itemSucesos -> {
            return new SucesosDasboardItemDetails(itemSucesos.getId(),PersonaMapper.toConcatNombre(itemSucesos.getAlumno().getPersona()),itemSucesos.getNivelGravedad(), itemSucesos.getSucesos().getNombre());
        }).toList();


        return new DashboardDirectoraDto(cantiUsuario, cantUsuarioActivos, totalSucesos,totalAlumnosSucesos,totalAsistenciahoy,totalAsistenciaActual,asistencias,sucesos);
    }

    public DashboardPsicologoBienestarDto getdasboardPsicologoBienestar(int id) {
        LocalDate fechaInicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        Long totalAtencionMes =  asignarRepository.contarEstadoYPsicologoEnMesActual(id,EstadoDocumento.CERRADO, fechaInicio,fechaFin) ;
        Long totalAtencion = asignarRepository.contarEstadoYPsicologoTotal(id, EstadoDocumento.CERRADO);

        Long totalSucesos =  sucesoRepository.contarTotalSucesosPorUsuario(id) ;
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(DayOfWeek.MONDAY);
        LocalDate finSemana = hoy.with(DayOfWeek.SUNDAY);
        Timestamp fechaInicioSemana = Timestamp.valueOf(inicioSemana.atStartOfDay());
        Timestamp fechaFinSemana = Timestamp.valueOf(finSemana.atTime(23, 59, 59));

        Long totalSucesosSemanal= sucesoRepository.contarSucesosPorUsuarioEnSemanaActual(fechaInicioSemana,fechaFinSemana) ;

        Long totalTareasPendiente= asignarRepository.contarPorEstadoYPsicologo(id, EstadoDocumento.PENDIENTE) ;
        Long totalTareas = asignarRepository.contarAsignacionesPorPsicologo(id);

        Dias diaActual = Dias.fromDayOfWeek(LocalDate.now().getDayOfWeek());

        Long asistenciahoy = horarioRepository.contarUsuariosPracticantesPorDia(diaActual);

        Long asistenciahoyActual = asistenciaRepository.contarAsistenciasPracticantesHoy();

        Long cantAlumnoMal = itemSucesoRepository.contarAlumnosUnicosEnItemSucesosDelMes();

        Long cantAlumnoBien = alumnoRepository.contarTotalAlumnos() - cantAlumnoMal;

        List<AsistenciaDasboardDto> asistencias = asistenciaRepository.listarAsistenciasHoyDePracticantes().stream().map(
                asistencia -> {
                    return new AsistenciaDasboardDto(asistencia.getUsuario().getId(),
                            PersonaMapper.toConcatNombre(asistencia.getUsuario().getPersona()),asistencia.getHora().toString(),asistencia.getEstado());
                }
        ).toList();

        List<TareasDasboardDto> tareas = asignarRepository.listarTareasEnviadasDePsicologos().stream().map(asignar -> {
            return new TareasDasboardDto(PersonaMapper.toConcatNombre(asignar.getPracticante().getPersona()), asignar.getTdocumento(),asignar.getFechaCreacion().toString());
        }).toList() ;

        List<DashboardDiagnosticoListDto> diagnosticos = atencionAlumnoRepository.obtenerTop5DiagnosticosUsados().stream().map(objects -> new DashboardDiagnosticoListDto(
                (String)  objects[0], ((Number) objects[1]).longValue()
        )).toList() ;

        List<SucesosDasboardItemDetails> sucesos = itemSucesoRepository.listarItemSucesosDelMesActual().stream().map(itemSucesos -> {
            return new SucesosDasboardItemDetails(itemSucesos.getId(),PersonaMapper.toConcatNombre(itemSucesos.getAlumno().getPersona()),itemSucesos.getNivelGravedad(), itemSucesos.getSucesos().getNombre());
        }).toList();

        return new DashboardPsicologoBienestarDto(totalAtencionMes, totalAtencion ,totalSucesos,totalSucesosSemanal,totalTareasPendiente, totalTareas ,asistenciahoy
                ,asistenciahoyActual , cantAlumnoBien, cantAlumnoMal, asistencias, tareas,diagnosticos,sucesos);
    }


    public DashboardBienestarDto getDasboardBienestar(int id) {
        Long cantALtaMes = itemSucesoRepository.contarTotalItemSucesosDelMesPorGravedad(NivelGravedad.ALTA);
        Long cantMedMes = itemSucesoRepository.contarTotalItemSucesosDelMesPorGravedad(NivelGravedad.MEDIA);
        Long cantBajaMes = itemSucesoRepository.contarTotalItemSucesosDelMesPorGravedad(NivelGravedad.BAJA);

        Long cantAlumnoMal = itemSucesoRepository.contarAlumnosUnicosEnItemSucesosDelMes();

        Long cantAlumnoBien = alumnoRepository.contarTotalAlumnos() - cantAlumnoMal;

        List<SucesosDasboardItemDetails> sucesos = itemSucesoRepository.listarItemSucesosDelMesActual().stream().map(itemSucesos -> {
            return new SucesosDasboardItemDetails(itemSucesos.getId(),PersonaMapper.toConcatNombre(itemSucesos.getAlumno().getPersona()),itemSucesos.getNivelGravedad(), itemSucesos.getSucesos().getNombre());
        }).toList();

        return new DashboardBienestarDto(cantALtaMes, cantMedMes, cantBajaMes,cantAlumnoBien, cantAlumnoMal, sucesos);
    }
}
