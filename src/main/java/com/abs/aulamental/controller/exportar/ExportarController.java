package com.abs.aulamental.controller.exportar;

import com.abs.aulamental.service.exportacion.exportarServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exportar")
@RequiredArgsConstructor
public class ExportarController {
    private final exportarServices exportarServices;

    @GetMapping("/suceso/{idSuceso}/{idAlumno}")
    public  ResponseEntity<byte[]> exportarSuceso(@PathVariable int idSuceso, @PathVariable int idAlumno) {
        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SucesoAlumno.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarSuceso(idSuceso, idAlumno));
    }

    @GetMapping("/sucesos/{idAlumno}")
    public ResponseEntity<byte[]> exportarSucesosAlumno(@PathVariable int idAlumno) {
        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SucesosAlumno.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarSucesos( idAlumno));
    }

    @GetMapping("/atenalumno/{idAtencion}/{idAlumno}")
    public ResponseEntity<byte[]> exportarAtencionAlumno(@PathVariable int idAtencion, @PathVariable int idAlumno) {
        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=FichaPsicologia.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarAtencionAlumno(idAtencion, idAlumno));
    }

    @GetMapping("/atenalumno/asistencia/{mes}/{anio}")
    public ResponseEntity<byte[]> exportarAtencionAlumnosAsistencia(@PathVariable int mes, @PathVariable int anio) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=Registro_Estudiantes.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarAtencionAlumnoAsistencia(mes,anio));

    }

    @GetMapping("/atenalumno/seguimineto/{id}")
    public ResponseEntity<byte[]> exportarAtencionAlumnoSeguimiento(@PathVariable int id) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Seguimiento.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarAtencionAlumnoSeguimineto(id));
    }


    @GetMapping("/atenapoderado/asistencia/{mes}/{anio}")
    public  ResponseEntity<byte[]> exportarAtencionApoderadosAsistencia(@PathVariable int mes, @PathVariable int anio){
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Asistencia_Padres.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarAtencionApoderadoAsistencia(mes,anio));
    }

    @GetMapping("/atenapoderado/{idatencion}/{idapoderado}")
    public  ResponseEntity<byte[]> exportarAtencionApoderado(@PathVariable int idatencion, @PathVariable int idapoderado) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=FichaPadres.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(exportarServices.exportarAtencionApoderado(idatencion,idapoderado));
    }

}
