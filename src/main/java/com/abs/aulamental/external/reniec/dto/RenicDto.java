package com.abs.aulamental.external.reniec.dto;

public record RenicDto(
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        String tipoDocumento,
        String numeroDocumento,
        String digitoVerificador
) {
}
