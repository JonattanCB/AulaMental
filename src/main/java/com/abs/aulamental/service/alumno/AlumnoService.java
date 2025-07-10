package com.abs.aulamental.service.alumno;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.dto.alumno.AlumnoDto;
import com.abs.aulamental.dto.alumno.AlumnoListDto;
import com.abs.aulamental.dto.alumno.AlumnoSucesosListDto;
import com.abs.aulamental.mapper.AlumnoMapper;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.repository.AlumnoRepository;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final ApoderadoService  apoderadoService;

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
}
