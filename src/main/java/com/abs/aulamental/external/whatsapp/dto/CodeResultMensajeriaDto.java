package com.abs.aulamental.external.whatsapp.dto;

import java.util.List;

public record CodeResultMensajeriaDto(
        String code,
        String message,
        ResultsDto results
) {
}
