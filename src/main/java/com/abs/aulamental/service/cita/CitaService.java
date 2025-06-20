package com.abs.aulamental.service.cita;

import com.abs.aulamental.repository.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitaService {
    private final CitaRepository citaRepository;


}
