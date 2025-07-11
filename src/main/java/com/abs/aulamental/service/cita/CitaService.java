package com.abs.aulamental.service.cita;

import com.abs.aulamental.dto.cita.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.CitaMapper;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.Cita;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.model.enums.EstadoCitas;
import com.abs.aulamental.repository.AlumnoRepository;
import com.abs.aulamental.repository.CitaRepository;
import com.abs.aulamental.repository.UsuarioRepository;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {
    private final CitaRepository citaRepository;
    private final AlumnoRepository alumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ApoderadoService  apoderadoService;

    @Transactional
    public CitaDetailsDto crearCita(CitaCreateDto dto) {
        // Validar fecha y hora
        if (dto.fecha().isBefore(LocalDate.now())) {
            throw new ValidarExcepciones("La fecha no puede ser en el pasado");
        }

        // Validar horario de atención
        LocalTime inicio = dto.hora();
        LocalTime fin = inicio.plusHours(2);

        LocalTime horarioInicio = LocalTime.of(8, 0);
        LocalTime horarioFin = LocalTime.of(18, 0);
        if (inicio.isBefore(horarioInicio) || fin.isAfter(horarioFin)) {
            throw new ValidarExcepciones("La cita debe estar entre 08:00 y 18:00");
        }

        // Validar alumno
        Alumno alumno = alumnoRepository.searchAlumnosById(dto.idAlumno());

        // Validar psicólogo
        Usuario psicologo = usuarioRepository.searchUsuarioById(dto.idPsicologo());

        // Verificar traslape
        List<Cita> existentes = citaRepository.findByPsicologoIdAndFecha(dto.idPsicologo(), dto.fecha());
        for (Cita existente : existentes) {
            LocalTime existenteInicio = existente.getHora();
            LocalTime existenteFin = existenteInicio.plusHours(2);

            boolean traslape = !(fin.isBefore(existenteInicio) || inicio.isAfter(existenteFin));
            if (traslape) {
                throw new ValidarExcepciones("El psicólogo ya tiene una cita en ese horario");
            }
        }

        // Crear cita
        Cita cita = new Cita();
        cita.setFecha(dto.fecha());
        cita.setHora(dto.hora());
        cita.setAlumno(alumno);
        cita.setPsicologo(psicologo);
        cita.setMotivo(dto.motivo());
        cita.setEstado(EstadoCitas.PENDIENTE);
        cita.setObservaciones(null);

        Cita guardada = citaRepository.save(cita);

        return CitaMapper.toDetails(guardada);
    }

    public Page<CitaAlumnoListDto> listarCitasPorAlumno(int idAlumno, Pageable pageable) {
        return citaRepository.findByAlumnoIdOrderByEstadoPriority(idAlumno, pageable)
                .map(CitaMapper::toListAlumnoDto);
    }

    public  Page<CitaPsicologoListDto> listarCitasPorPsicologo(int idPsicologo, Pageable pageable) {
        return citaRepository.findByPsicologoIdOrderByEstadoPriority(idPsicologo, pageable)
                .map(cita -> {
                    String contact = apoderadoService.contact1ApoderadoAlumno(cita.getAlumno().getId());
                    return CitaMapper.toListPsicologoDto(cita, contact);
                });
    }

    @Transactional
    public CitaDetailsDto actualizarEstadoCita(CitaStatusUpdateDto dto) {
        Cita cita = citaRepository.searchCitaById(dto.id());

        if (dto.estado() == EstadoCitas.PENDIENTE) {
            throw new ValidarExcepciones("No se puede cambiar el estado a PENDIENTE");
        }

        cita.ActualizarEstado(dto.estado(), dto.observaciones());

        return CitaMapper.toDetails(cita);
    }

    public CitaPsicologoDetailsDto countCitasPorPsicologo(int idPsicologo) {
        long pendientes = citaRepository.countByPsicologoIdAndEstado(idPsicologo, EstadoCitas.PENDIENTE);
        long confirmadas = citaRepository.countByPsicologoIdAndEstado(idPsicologo, EstadoCitas.CONFIRMADA);
        long canceladas = citaRepository.countByPsicologoIdAndEstado(idPsicologo, EstadoCitas.CANCELADA);
        long total = pendientes + confirmadas + canceladas;
        return  new CitaPsicologoDetailsDto(total,pendientes,confirmadas,canceladas);
    }

    public List<CitaPsicologoSelectDto> listarPsicologoSelect(String nombre) {
        return  usuarioRepository.listUsuarioByPsicologoAndNombre(nombre).stream().filter(usuario -> usuario.getEstado()== Estado.ACTIVO)
                .map(usuario -> {
                    return new CitaPsicologoSelectDto(usuario.getId(), PersonaMapper.toConcatNombre(usuario.getPersona()));
                }).toList();
    }

}
