package com.abs.aulamental.dto.exportar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtencionAlumnosSeguiminetoEsportar {
    private String fecha;
    private String numero;
    private String motivo_consulta;
    private String resumen_entrevista;
    private String conclusiones;
    private String recomendaciones;
    private String herramientas;
    private String diag_presunt;
    private String comentario_adicional;
}
