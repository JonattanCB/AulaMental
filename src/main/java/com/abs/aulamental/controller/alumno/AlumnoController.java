package com.abs.aulamental.controller.alumno;

import com.abs.aulamental.dto.alumno.AlumnoDto;
import com.abs.aulamental.dto.alumno.AlumnoListDto;
import com.abs.aulamental.dto.alumno.AlumnoSucesosListDto;
import com.abs.aulamental.service.alumno.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

}
