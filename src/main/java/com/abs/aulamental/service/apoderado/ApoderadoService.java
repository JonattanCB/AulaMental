package com.abs.aulamental.service.apoderado;

import com.abs.aulamental.repository.ApoderadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApoderadoService {
    private final ApoderadoRepository apoderadoRepository;

    public String contact1ApoderadoAlumno(int idAlumno){
        return apoderadoRepository.searchApoderadoByAlumno(idAlumno).getPersona().getTelefono1();
    }

    public String contact2ApoderadoAlumno(int idAlumno){
        return apoderadoRepository.searchApoderadoByAlumno(idAlumno).getPersona().getTelefono2();
    }
}
