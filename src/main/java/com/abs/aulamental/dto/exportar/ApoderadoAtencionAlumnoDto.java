package com.abs.aulamental.dto.exportar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApoderadoAtencionAlumnoDto {
    private String tipoApoderado;
    private String nombreApoderado;
    private String ocupacionApoderado;
    private String apoderadoVive;
}
