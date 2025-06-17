package com.abs.aulamental.service.horario;

import com.abs.aulamental.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HorarioService {
    private final HorarioRepository horarioRepository;

    public boolean deleteScheduleByUserId(Integer idUsuario) {
        return horarioRepository.deleteAllByUsuarioId(idUsuario) > 0;
    }

}
