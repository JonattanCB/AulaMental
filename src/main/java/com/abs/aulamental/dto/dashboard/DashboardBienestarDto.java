package com.abs.aulamental.dto.dashboard;

import com.abs.aulamental.dto.suceso.SucesosDasboardItemDetails;

import java.util.List;

public record DashboardBienestarDto(
        Long cantALtaMes,
        Long cantMedMes,
        Long cantBajaMes,
        Long cantAlumnoBien,
        Long cantAlumnoMal,
        List<SucesosDasboardItemDetails> sucesos
) {
}
