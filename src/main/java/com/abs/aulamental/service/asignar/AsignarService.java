package com.abs.aulamental.service.asignar;

import com.abs.aulamental.dto.asignar.*;
import com.abs.aulamental.dto.atencionalumno.AtenAlumnoDetailDto;
import com.abs.aulamental.dto.atencionalumno.AtenAlumnoUpdateDto;
import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoDetailDto;
import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoUpdateDto;
import com.abs.aulamental.dto.user.PracticanteListDetailsDto;
import com.abs.aulamental.mapper.*;
import com.abs.aulamental.model.Asignar;
import com.abs.aulamental.model.AtencionAlumno;
import com.abs.aulamental.model.AtencionApoderados;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import com.abs.aulamental.repository.*;
import com.abs.aulamental.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignarService {
    private final ApoderadoRepository apoderadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final AsignarRepository asignarRepository;
    private final AtencionAlumnoRepository atencionAlumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtencionApoderadosRepository atencionApoderadosRepository;
    private final DiagnosticoRepository diagnosticoRepository;

    @Transactional
    public AsignarDetailsDto createAsignar(AsignarCreateUserDto dto) {
        var psicologo = usuarioRepository.searchUsuarioById(dto.idPsicologo());
        var practicante = usuarioRepository.searchUsuarioById(dto.idPracticante());
        int idDocumentacion = 0;
        if (dto.tipodocumentacion() == Tipodocumentacion.ATENCIONALUMNO){
            var alumno = alumnoRepository.searchAlumnosById(dto.idPersonal());
            var aal = atencionAlumnoRepository.save(new AtencionAlumno(0,alumno,"","","","","",null,
                    "", DateUtil.getTodaySqlDate(),DateUtil.nowTimestamp(),DateUtil.nowTimestamp()));
            idDocumentacion = aal.getId();
        }

        if (dto.tipodocumentacion() == Tipodocumentacion.ATENCIONAPODERADO){
            var apoderado = apoderadoRepository.searchApoderadoById(dto.idPersonal());
            var aap = atencionApoderadosRepository.save(new AtencionApoderados(0,apoderado,"","","","","",
                    DateUtil.getTodaySqlDate(),"",DateUtil.nowTimestamp(),DateUtil.nowTimestamp()));
            idDocumentacion = aap.getId();
        }

        var asignacion = asignarRepository.save(AsignarMappper.toCreateAsignar(new AsignarCreateDto(dto.idPsicologo(),dto.idPracticante()
                ,dto.tipodocumentacion(),idDocumentacion, EstadoDocumento.PENDIENTE,""),psicologo,practicante));

        return AsignarMappper.toAsignarDetailsDto(asignacion);
    }

    public Page<AsignarListPracticantesDto> listAsignar(String nombre, Pageable pageable) {
        return asignarRepository.listAsignarOptionNombre(nombre,pageable).map(AsignarMappper::toAsignarListPracticantesDto);
    }

    public Page<AsignarTaskListDto> listAsignacionPracticantes(int id, LocalDate fecha, Pageable pageable) {
        return asignarRepository.listAsignarPracticanteOptionFecha(id,fecha, pageable).map(asignar -> {
            var nombre = obtenerNombrePaciente(asignar.getId());
            return AsignarMappper.toAsignarTaskList(asignar,nombre);
        });
    }

    @Transactional
    public AtenAlumnoDetailDto updateAtencionAlumno(AtenAlumnoUpdateDto dto) {
        int idaal = searchIdDocumentbyIdAsignacion(dto.idAsignacion());
        var diagnostico = diagnosticoRepository.searchDiagnosticoById(dto.idDiagnostico());
        AtencionAlumno aal = atencionAlumnoRepository.searchAtencionAlumnoById(idaal);
        aal.ActualizarDatos(dto,diagnostico);
        changerStatus(EstadoDocumento.ENVIADO,dto.idAsignacion());
        return AtencionAlumnoMapper.toDetailAtenAlumno(aal);
    }

    @Transactional
    public AtenApoderadoDetailDto updateAtencionApoderado(AtenApoderadoUpdateDto dto) {
        int idaap = searchIdDocumentbyIdAsignacion(dto.idAsignacion());
        AtencionApoderados aap = atencionApoderadosRepository.searchAtencionApoderadosById(idaap);
        aap.ActualizarDatos(dto);
        changerStatus(EstadoDocumento.ENVIADO,dto.idAsignacion());
        return AtencionApoderadoMapper.toDetailAtenApoderado(aap);
    }

    public AsignarPracticanteDetailsDto getPracticanteDetails(int id){
        long pendiente = asignarRepository.contarPorEstadoYPracticante(id, EstadoDocumento.PENDIENTE);
        long enviados = asignarRepository.contarPorEstadoYPracticante(id,EstadoDocumento.ENVIADO);
        long revicion = asignarRepository.contarPorEstadoYPracticante(id, EstadoDocumento.REVISADO);
        long completados = asignarRepository.contarPorEstadoYPracticante(id, EstadoDocumento.CERRADO);
        long total = pendiente + enviados + revicion + completados;
        return new AsignarPracticanteDetailsDto(total,pendiente,enviados,revicion,completados);
    }

    private int searchIdDocumentbyIdAsignacion(int id){
        var asignacion = asignarRepository.searchAsignarsById(id);
        return asignacion.getIdDocumento();
    }

    private String obtenerNombrePaciente(int id){
        var asignacion = asignarRepository.searchAsignarsById(id);
        if (asignacion.getTdocumento().equals(Tipodocumentacion.ATENCIONALUMNO)){
            var documento = atencionAlumnoRepository.searchAtencionAlumnoById(asignacion.getIdDocumento());
            return PersonaMapper.toConcatNombre(documento.getAlumno().getPersona());
        }

        if (asignacion.getTdocumento().equals(Tipodocumentacion.ATENCIONAPODERADO)){
            var documento = atencionApoderadosRepository.searchAtencionApoderadosById(asignacion.getIdDocumento());
            return PersonaMapper.toConcatNombre(documento.getApoderado().getPersona());
        }
        return "no asignado";
    }


    private Asignar changerStatus(EstadoDocumento estado, int idAsignacion){
        var asignacion = asignarRepository.searchAsignarsById(idAsignacion);
        asignacion.ActualizarEstado(estado);
        return asignacion;
    }

    private void changerObservacion(String observacion, Asignar asignar){
        asignar.ActualizarObservaciones(observacion);
    }

    @Transactional
    public AsignarDetailsDto updateAsignarAporbado(int id) {
        return  AsignarMappper.toAsignarDetailsDto(changerStatus(EstadoDocumento.CERRADO, id));
    }


    @Transactional
    public AsignarDetailsDto updateAsignarRechazado(int id, String observacion) {
        var asignar = changerStatus(EstadoDocumento.REVISADO, id);
        changerObservacion(observacion, asignar);
        return  AsignarMappper.toAsignarDetailsDto(asignar);
    }

    public List<PracticanteListDetailsDto> listPracticanteDetails(String nombre) {
        return usuarioRepository.listUsuarioPracticante(nombre).stream().map(usuario -> {
            long cantPendiente = asignarRepository.contarPorEstadoYPracticante(usuario.getId(),EstadoDocumento.PENDIENTE);
            long cantCerrado = asignarRepository.contarPorEstadoYPracticante(usuario.getId(),EstadoDocumento.CERRADO);
            return  UsuarioMapper.toPracticantesDetailsDto(usuario, cantPendiente,cantCerrado);
        }).toList();
    }




}
