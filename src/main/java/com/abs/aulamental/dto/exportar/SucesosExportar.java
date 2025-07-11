package com.abs.aulamental.dto.exportar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SucesosExportar {
    private String ID;
    private String fecha;
    private String nombre;
    private String edad;
    private String grado;
    private String motivo;
    private String suceso;
    private String detalles;
    private String argumentacion;
    private String acciones;
}
