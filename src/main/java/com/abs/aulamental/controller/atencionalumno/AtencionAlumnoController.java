package com.abs.aulamental.controller.atencionalumno;

import com.abs.aulamental.dto.atencionalumno.*;
import com.abs.aulamental.service.atencionalumno.AtencionAlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/atencionalumno")
@RequiredArgsConstructor
public class AtencionAlumnoController {
    private  final AtencionAlumnoService atencionAlumnoService;

    @PostMapping("/create/psicologo")
    public ResponseEntity<AtenAlumnoDetailDto> createAtenAlumnoPsicolgo(@RequestBody @Valid AtenAlumnoCreateDto dto){
        return ResponseEntity.ok(atencionAlumnoService.createAtenAlumnoPsicologo(dto));
    }

    @GetMapping("/list/alumnos")
    public ResponseEntity<Page<AtenAlumnoListDto>> listAtenAlumno(@RequestParam(required = false) String nombre, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(atencionAlumnoService.listAtenAlumno(nombre, pageable));
    }

    @GetMapping("/list/alumnos/{id}")
    public ResponseEntity<Page<AtenAlumnoDetailListDto>> listAtenAlumnoDetalis(@PathVariable int id, @RequestParam(required = false) Date date, Pageable pageable){
        return ResponseEntity.ok(atencionAlumnoService.listAtenALumnoDetalis(id, date, pageable ));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<AtenAlumnoDto> getAtenAlumno (@PathVariable int id){
        return ResponseEntity.ok(atencionAlumnoService.getAtenAlumno(id));
    }

}
