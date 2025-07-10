package com.abs.aulamental.dto.dashboard;

import com.abs.aulamental.dto.asignar.TareasDasboardDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDasboardDto;
import com.abs.aulamental.dto.diagnostico.DashboardDiagnosticoListDto;
import com.abs.aulamental.dto.suceso.SucesosDasboardItemDetails;

import java.util.List;

public record DashboardPsicologoBienestarDto(
        Long totalAtencionMes,
        Long totalAtencion,
        Long totalSucesos,
        Long totalSucesosSemanal,
        Long totalTareasPendiente,
        Long totalTareas,
        Long asistenciahoy,
        Long asistenciahoyActual,
        Long cantAlumnoBien,
        Long cantAlumnoMal,
        List<AsistenciaDasboardDto> asistencias,
        List<TareasDasboardDto> tareas,
        List<DashboardDiagnosticoListDto> diagnosticos,
        List<SucesosDasboardItemDetails> sucesos
) {
}
