package com.abs.aulamental.dto.dashboard;

import com.abs.aulamental.dto.asignar.TareasDasrboardPracticanteDto;

import java.util.List;

public record DashboardPracticanteDto(
        Long perdientes,
        Long cerrados,
        Long revisados,
        Long enviados,
        List<TareasDasrboardPracticanteDto> tareas
) {
}
