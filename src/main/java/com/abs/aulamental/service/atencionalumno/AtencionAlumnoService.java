package com.abs.aulamental.service.atencionalumno;

import com.abs.aulamental.dto.asignar.AsignarCreateDto;
import com.abs.aulamental.dto.atencionalumno.*;
import com.abs.aulamental.mapper.AlumnoMapper;
import com.abs.aulamental.mapper.AsignarMappper;
import com.abs.aulamental.mapper.AtencionAlumnoMapper;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.Diagnostico;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import com.abs.aulamental.repository.*;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AtencionAlumnoService {
    private final AtencionAlumnoRepository atencionAlumnoRepository;
    private final AlumnoRepository alumnoRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignarRepository asignarRepository;
    private final ApoderadoService apoderadoService;

    public AtenAlumnoDetailDto createAtenAlumnoPsicologo(AtenAlumnoCreateDto dto) {
        var psicologo = usuarioRepository.searchUsuarioById(dto.idUsuario());
        var alumno = alumnoRepository.searchAlumnosById(dto.idAlumno());
        var diagnostico = diagnosticoRepository.searchDiagnosticoById((dto.idDiagnostico()));

        comprobarValidacionCreate(psicologo,alumno,diagnostico);

        var atenalumno =atencionAlumnoRepository.save(AtencionAlumnoMapper.toCreateAtenAlumnoPsicologo(dto,alumno,diagnostico));
        asignarRepository.save(AsignarMappper.toCreateAsignar(new AsignarCreateDto(
                dto.idUsuario(), dto.idUsuario(), Tipodocumentacion.ATENCIONALUMNO,atenalumno.getId(), EstadoDocumento.CERRADO,""
        ), psicologo, psicologo));

        return AtencionAlumnoMapper.toDetailAtenAlumno(atenalumno);
    }

    public Page<AtenAlumnoListDto> listAtenAlumno(String nombre, Pageable pageable) {
        return alumnoRepository.getAlumnosOptionNombre(nombre, pageable).map(alumno -> {
            String contact1 = apoderadoService.contact1ApoderadoAlumno(alumno.getId());
            String contact2 = apoderadoService.contact2ApoderadoAlumno(alumno.getId());
            return AtencionAlumnoMapper.toListAtenAlumno(alumno,contact1,contact2);
        });
    }

    public Page<AtenAlumnoDetailListDto> listAtenALumnoDetalis(int id, Date date, Pageable pageable) {
        return atencionAlumnoRepository.getAtencionesByAlumnoIdOptionFecha(id, date, pageable).map(AtencionAlumnoMapper::toListAtenAlumnoDetalis);
    }

    public AtenAlumnoDto getAtenAlumno(int id) {
        return AtencionAlumnoMapper.toDto(atencionAlumnoRepository.searchAtencionAlumnoById(id));
    }

    private void comprobarValidacionCreate(Usuario usuario, Alumno alumno, Diagnostico diagnostico){
        if (alumno == null || usuario == null || diagnostico == null) {
            throw new IllegalArgumentException("Alumno, Usuario or Diagnostico not found");
        }
    }

}