package com.abs.aulamental.dto.exportar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtencionApoderadoAsistenciaDto {
    private String nombre;
    private String fecha;
    private String hora;
    private String grado;
    private String firma;
}
