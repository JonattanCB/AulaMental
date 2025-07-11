package com.abs.aulamental.dto.exportar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtencionAlumnoAsistenciaDto {
    private String alumno;
    private String grado;
    private String fecha;
    private String hora;
    private String firma;

}
