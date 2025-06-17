package com.abs.aulamental.controller.diagnostico;

import com.abs.aulamental.dto.diagnostico.*;
import com.abs.aulamental.model.Diagnostico;
import com.abs.aulamental.service.diagnostico.DiagnosticoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/diagnostico")
@RequiredArgsConstructor
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    @GetMapping("/list")
    public ResponseEntity<Page<DiagnosticoListDto>> listDiagnostico(@RequestParam(required = false) String nombre, Pageable pageable) {
        return ResponseEntity.ok(diagnosticoService.listDiagnostico(nombre, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<DiagnosticoDetailDto> createDiagnostico(@RequestBody @Valid DiagnosticoCreateDto dto){
        return ResponseEntity.ok(diagnosticoService.createDiagnostico(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoDto> getDiagnostico(@PathVariable int id){
        return ResponseEntity.ok(diagnosticoService.getDiagnostico(id));
    }


    @PutMapping("/update")
    @Transactional
    public  ResponseEntity<DiagnosticoDetailDto> updateDiagnostico(@RequestBody @Valid DiagnosticoUpdateDto dto){
        return  ResponseEntity.ok(diagnosticoService.updateDiagnostico(dto));
    }

    @PutMapping("/update/estado/{id}")
    @Transactional
    public ResponseEntity<DiagnosticoDetailDto> changeStatus(@PathVariable int id){
        return ResponseEntity.ok(diagnosticoService.changeStatus(id));
    }

}
