package com.abs.aulamental.service.asignar;

import com.abs.aulamental.dto.asignar.*;
import com.abs.aulamental.dto.atencionalumno.AtenAlumnoDetailDto;
import com.abs.aulamental.dto.atencionalumno.AtenAlumnoUpdateDto;
import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoDetailDto;
import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoUpdateDto;
import com.abs.aulamental.mapper.AsignarMappper;
import com.abs.aulamental.mapper.AtencionAlumnoMapper;
import com.abs.aulamental.mapper.AtencionApoderadoMapper;
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

import java.sql.Date;

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

    public Page<AsignarTaskListDto> listAsignacionPracticantes(int id, Date fecha, Pageable pageable) {
        return asignarRepository.listAsignarPracticanteOptionFecha(id,fecha, pageable).map(AsignarMappper::toAsignarTaskList);
    }

    public AtenAlumnoDetailDto updateAtencionAlumno(AtenAlumnoUpdateDto dto) {
        int idaal = searchIdDocumentbyIdAsignacion(dto.idAsignacion());
        var diagnostico = diagnosticoRepository.searchDiagnosticoById(dto.idDiagnostico());
        AtencionAlumno aal = atencionAlumnoRepository.searchAtencionAlumnoById(idaal);
        aal.ActualizarDatos(dto,diagnostico);
        changerStatus(EstadoDocumento.ENVIADO,dto.idAsignacion());
        return AtencionAlumnoMapper.toDetailAtenAlumno(aal);
    }

    public AtenApoderadoDetailDto updateAtencionApoderado(AtenApoderadoUpdateDto dto) {
        int idaap = searchIdDocumentbyIdAsignacion(dto.idAsignacion());
        AtencionApoderados aap = atencionApoderadosRepository.searchAtencionApoderadosById(idaap);
        aap.ActualizarDatos(dto);
        changerStatus(EstadoDocumento.ENVIADO,dto.idAsignacion());
        return AtencionApoderadoMapper.toDetailAtenApoderado(aap);
    }

    private int searchIdDocumentbyIdAsignacion(int id){
        var asignacion = asignarRepository.searchAsignarsById(id);
        return asignacion.getIdDocumento();
    }

    private Asignar changerStatus(EstadoDocumento estado, int idAsignacion){
        var asignacion = asignarRepository.searchAsignarsById(idAsignacion);
        asignacion.ActualizarEstado(estado);
        return asignacion;
    }

    private void changerObservacion(String observacion, Asignar asignar){
        asignar.ActualizarObservaciones(observacion);
    }

    public AsignarDetailsDto updateAsignarAporbado(int id) {
        return  AsignarMappper.toAsignarDetailsDto(changerStatus(EstadoDocumento.CERRADO, id));
    }


    public AsignarDetailsDto updateAsignarRechazado(int id, String observacion) {
        var asignar = changerStatus(EstadoDocumento.REVISADO, id);
        changerObservacion(observacion, asignar);
        return  AsignarMappper.toAsignarDetailsDto(asignar);
    }

}
