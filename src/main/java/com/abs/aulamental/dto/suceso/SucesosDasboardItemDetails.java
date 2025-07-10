package com.abs.aulamental.dto.suceso;

import com.abs.aulamental.model.enums.NivelGravedad;

public record SucesosDasboardItemDetails(
        int id,
        String nombre,
        NivelGravedad nivel,
        String suceso
) {
}
