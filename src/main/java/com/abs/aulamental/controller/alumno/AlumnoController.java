package com.abs.aulamental.controller.alumno;

import com.abs.aulamental.dto.alumno.*;
import com.abs.aulamental.service.alumno.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumno")
@RequiredArgsConstructor
public class AlumnoController {
    private final AlumnoService alumnoService;

    @GetMapping("/list")
    public ResponseEntity<List<AlumnoSucesosListDto>> getAlumnosOptionNombreSucesoList(@RequestParam(required = false)  String nombre){
        return ResponseEntity.ok(alumnoService.getAllAlumnoSucesos(nombre));
    }

    @GetMapping("/list/page")
    public  ResponseEntity<Page<AlumnoListDto>> getALumnosOptionNombrePage( @RequestParam(required = false) String nombre,  Pageable pageable) {
        return ResponseEntity.ok(alumnoService.getAllAlumnos(nombre, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> getAlumnoById(@PathVariable int id){
        return ResponseEntity.ok(alumnoService.getAlumnoById(id));
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<AlumnoDto> create(@RequestBody CreateAlumnoDto dto){
        return ResponseEntity.ok(alumnoService.createALumno(dto));
    }

    @PutMapping("/editar")
    @Transactional
    public ResponseEntity<AlumnoDto> updateAlumno(@RequestBody UpdateAlumnoDto dto){
        return ResponseEntity.ok(alumnoService.updateAlumno(dto));
    }

    @PutMapping("/actualzarEstado/{id}")
    @Transactional
    public  ResponseEntity<String> actualizarEstado(@PathVariable int id){
        return ResponseEntity.ok(alumnoService.updateStatus(id));
    }

}
