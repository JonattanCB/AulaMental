package com.abs.aulamental.dto.exportar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtencionApoderadoExportar {
    private String numero;
    private String fecha;
    private String nombre;
    private String edad;
    private String gradoNivel;
    private String hijo;
    private String direccion;
    private String telefono;
    private String motivo_consulta;
    private String resumen_entrevista;
    private String conclusiones;
    private String recomendaciones;
    private String intervencion;
    private String comentario_adicional;
}
