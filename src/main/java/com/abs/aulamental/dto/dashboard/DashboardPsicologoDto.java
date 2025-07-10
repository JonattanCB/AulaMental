package com.abs.aulamental.dto.dashboard;

import com.abs.aulamental.dto.asignar.TareasDasboardDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDasboardDto;
import com.abs.aulamental.dto.diagnostico.DashboardDiagnosticoListDto;

import java.util.List;

public record DashboardPsicologoDto(
        Long totalAtencionMes,
        Long totalAtencion,
        Long totalTareasPendiente,
        Long totalTareas,
        Long asistenciahoy,
        Long asistenciahoyActual,
        List<AsistenciaDasboardDto> asistencias,
        List<TareasDasboardDto> tareas,
        List<DashboardDiagnosticoListDto> diagnosticos
) {
}
