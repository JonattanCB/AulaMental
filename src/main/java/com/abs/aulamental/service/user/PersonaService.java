package com.abs.aulamental.service.user;

import com.abs.aulamental.external.reniec.ReniecClient;
import com.abs.aulamental.external.reniec.dto.RenicDto;
import com.abs.aulamental.mapper.PersonaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PersonaService {
    private final ReniecClient reniecClient;

    public RenicDto getPersonaPorDni(String dni){
        return PersonaMapper.toFormatReniec(reniecClient.buscarPorDni(dni));
    }

}
