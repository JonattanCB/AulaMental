package com.abs.aulamental.controller.cita;

import com.abs.aulamental.dto.cita.*;
import com.abs.aulamental.service.cita.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cita")
@RequiredArgsConstructor
public class CitaController {
    private final CitaService citaService;

    @PostMapping("/crear")
    public ResponseEntity<CitaDetailsDto> crearCita(@RequestBody CitaCreateDto request) {
        return ResponseEntity.ok(citaService.crearCita(request));
    }

    @GetMapping("/alumno/{idAlumno}")
    public ResponseEntity<Page<CitaAlumnoListDto>> listAlumnoCitas(@PathVariable int idAlumno, Pageable pageable) {
        return ResponseEntity.ok(citaService.listarCitasPorAlumno(idAlumno, pageable));
    }

    @GetMapping("/psicologo/list")
    public  ResponseEntity<List<CitaPsicologoSelectDto>> listPsicologoSelect(@RequestParam(required = false) String nombre){
        return  ResponseEntity.ok(citaService.listarPsicologoSelect(nombre));
    }


    @GetMapping("/psicologo/{idPsicologo}")
    public ResponseEntity<Page<CitaPsicologoListDto>> listPsicologoCitas(@PathVariable int idPsicologo, Pageable pageable) {
        return ResponseEntity.ok(citaService.listarCitasPorPsicologo(idPsicologo, pageable));
    }

    @GetMapping("/psicologo/count/{idPsicologo}")
    public ResponseEntity<CitaPsicologoDetailsDto> countCitasPsicologo(@PathVariable int idPsicologo) {
        return ResponseEntity.ok(citaService.countCitasPorPsicologo(idPsicologo));
    }

    @PutMapping("/estado")
    @Transactional
    public ResponseEntity<CitaDetailsDto> actualizarEstadoCita(@RequestBody CitaStatusUpdateDto dto) {
        return ResponseEntity.ok(citaService.actualizarEstadoCita(dto));
    }
}
