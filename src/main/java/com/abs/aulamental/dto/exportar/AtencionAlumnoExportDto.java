package com.abs.aulamental.dto.exportar;

import java.util.List;

public record AtencionAlumnoExportDto(
        String numero,
        String fecha,
        String nombre,
        String grado,
        String nivel,
        String fechaNacimiento,
        String lugarNacimiento,
        String edad,
        String telefono,
        String direccion,
        String motivo_consulta,
        String resumen_entrevista,
        String conclusiones,
        String recomendaciones,
        String tecnicas,
        String diagnostico_presuntivo,
        String comentario_adicional,
        List<ApoderadoAtencionAlumnoDto> apoderados
) {
}
