package com.abs.aulamental.service.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoCreateDto;
import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.dto.Apoderado.ApoderadoUpdateDto;
import com.abs.aulamental.dto.alumno.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.AlumnoMapper;
import com.abs.aulamental.mapper.ApoderadoMapper;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.Apoderado;
import com.abs.aulamental.model.Persona;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.repository.AlumnoRepository;
import com.abs.aulamental.repository.ApoderadoRepository;
import com.abs.aulamental.repository.PersonasRepository;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final ApoderadoService  apoderadoService;
    private final ApoderadoRepository apoderadoRepository;
    private final PersonasRepository personasRepository;

    public List<AlumnoSucesosListDto> getAllAlumnoSucesos(String nombre){
        return alumnoRepository.getAlumnosOptionNombretoList(nombre).stream().map(AlumnoMapper::tolistAlumnoitemSuceso).toList();
    }

    public Page<AlumnoListDto> getAllAlumnos(String nombre, Pageable pageable){
        Page<Alumno> alumnosPage = alumnoRepository.getAlumnosOptionNombretoPage(nombre, pageable);
        return alumnosPage.map(alumno -> {
            String contact1 = apoderadoService.contact1ApoderadoAlumno(alumno.getId());
            return AlumnoMapper.tolistAlumno(alumno, contact1);
        });
    }

    public AlumnoDto getAlumnoById(int id) {
        var alumno = alumnoRepository.searchAlumnosById(id);
        List<ApoderadoDto> apoderadoDtos =apoderadoService.getApoderadosByAlumnoId(id);
        return AlumnoMapper.toDto(alumno, apoderadoDtos);
    }

    @Transactional
    public AlumnoDto createALumno(CreateAlumnoDto dto) {
        validacionCreateAlumno(dto);
        var alumno = alumnoRepository.save(AlumnoMapper.tocreateDto(dto));

        for (ApoderadoCreateDto apdto : dto.apoderados()){
            var apoderado = ApoderadoMapper.toCreateApoderado(apdto,alumno);
            Persona persona = personasRepository.findByNdocumento(apdto.ndocumento());
            if(persona == null){
                persona = personasRepository.save(apoderado.getPersona());
            }

            boolean yaExiste = apoderadoRepository.existsByPersonaIdAndAlumnoId(persona.getId(), alumno.getId());

            if (!yaExiste) {
                apoderado.setPersona(persona);
                apoderadoRepository.save(apoderado);
            }
        }

        return getAlumnoById(alumno.getId());
    }

    private void validacionCreateAlumno(CreateAlumnoDto dto){
        if (personasRepository.existsPersonaByNdocumento(dto.ndocumento())) {
            throw new ValidarExcepciones("La persona ya está registrada en el sistema.");
        }
    }

    @Transactional
    public AlumnoDto updateAlumno(UpdateAlumnoDto dto) {
        var alumno = alumnoRepository.searchAlumnosById(dto.idAlumno());

        alumno.ActualizarNivelGrado(dto.grado(), dto.nivel());

        Map<Integer, Apoderado> apoderadosActuales = alumno.getApoderados().stream()
                .collect(Collectors.toMap(Apoderado::getId, a -> a));

        for (ApoderadoUpdateDto apdto : dto.apoderados()) {
            Apoderado apoderado = apoderadosActuales.get(apdto.idAPoderado());
            if (apoderado != null) {
                apoderado.actualizarData(apdto.opcipacion());
                apoderado.getPersona().updatePersonaData(apdto.telefono(), apdto.telefono1(),apdto.correop(),apdto.direccion());
            }
        }

        return  getAlumnoById(alumno.getId());
    }

    @Transactional
    public String updateStatus(int id) {
        var alumno = alumnoRepository.searchAlumnosById(id);
        if (alumno.getEstado() == Estado.ACTIVO){
            alumno.ActualizarEstado(Estado.INACTIVO);
        }else {
            alumno.ActualizarEstado(Estado.ACTIVO);
        }
        return "Estado Actualizado";
    }
}
