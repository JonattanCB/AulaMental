package com.abs.aulamental.dto.dashboard;

import com.abs.aulamental.dto.asistencia.AsistenciaDasboardDto;
import com.abs.aulamental.dto.suceso.SucesosDasboardItemDetails;

import java.util.List;

public record DashboardDirectoraDto(
        Long cantiUsuario,
        Long cantiUsuarioActivo,
        Long totalSucesos,
        Long totalAlumnosSucesos,
        Long totalAsistenciahoy,
        Long totalAsistenciaActual,
        List<AsistenciaDasboardDto> asistencias,
        List<SucesosDasboardItemDetails> sucesos
) {
}
