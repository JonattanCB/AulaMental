package com.abs.aulamental.controller.suceso;

import com.abs.aulamental.dto.suceso.*;
import com.abs.aulamental.service.suceso.SucesoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/suceso")
@RequiredArgsConstructor
public class SucesoController {
    private final SucesoService sucesoService;

    @PostMapping("/create")
    public ResponseEntity<SucesoDetailDto> createSuceso(@RequestBody @Valid  SucesoCreateDto dto){
        return ResponseEntity.ok(sucesoService.createSuceso(dto));
    }

    @GetMapping("/alumnos/list")
    public ResponseEntity<Page<SucesoAlumnoListDto>> listAlumnos(@RequestParam(required = false) String nombre, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(sucesoService.listAlumnosSuceso(nombre, pageable));
    }

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Page<ItemSucesoDto>> listItemSuceso( @PathVariable int id, @RequestParam(required = false) String nombre ,
                                                              @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(sucesoService.listItemSuceso(id, nombre,pageable));
    }

    @GetMapping("/alumno/details/{id}")
    public  ResponseEntity<SucesosAlumnoDetailsDto> getDetailSuceso( @PathVariable int id){
        return ResponseEntity.ok(sucesoService.toDetailsSucesoAlumno(id));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<SucesoDto> getSucesoById(@PathVariable int id){
        return ResponseEntity.ok(sucesoService.getSucesos(id));
    }


}
