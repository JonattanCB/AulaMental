package com.abs.aulamental.service.apoderado;

import com.abs.aulamental.dto.Apoderado.ApoderadoDto;
import com.abs.aulamental.mapper.ApoderadoMapper;
import com.abs.aulamental.repository.ApoderadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApoderadoService {
    private final ApoderadoRepository apoderadoRepository;

    @Transactional
    public String contact1ApoderadoAlumno(int idAlumno){
        return apoderadoRepository.searchApoderadoByAlumno(idAlumno).get(0).getPersona().getTelefono1();
    }

    @Transactional
    public String contact2ApoderadoAlumno(int idAlumno){
        return apoderadoRepository.searchApoderadoByAlumno(idAlumno).get(0).getPersona().getTelefono2();
    }

    public List<ApoderadoDto> getApoderadosByAlumnoId(int idAlumno) {
        return apoderadoRepository.searchApoderadoByAlumno_Id(idAlumno).stream()
                .map(ApoderadoMapper::toDto)
                .toList();
    }

}
