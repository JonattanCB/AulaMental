package com.abs.aulamental.controller.atencionapoderado;

import com.abs.aulamental.dto.Apoderado.ApoderadoDetailsSelectDto;
import com.abs.aulamental.dto.atencionapoderado.*;
import com.abs.aulamental.service.atencionapoderado.AtencionApoderadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/atencionapoderado")
@RequiredArgsConstructor
public class AtencionApoderadoController {
    private final AtencionApoderadoService atencionApoderadoService;

    @PostMapping("/create/psicologia")
    public ResponseEntity<AtenApoderadoDetailDto> createAtenApoderadoPsicolgia(@RequestBody @Valid AtenApoderadoCreateDto dto){
        return ResponseEntity.ok(atencionApoderadoService.createAtenApoderadoPsicologia(dto));
    }

    @GetMapping("/list/apoderados")
    public ResponseEntity<Page<AtenApoderadoListDto>> listAtenApoderado(@RequestParam(required = false) String nombre, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(atencionApoderadoService.listAtenApoderado(nombre, pageable));
    }

    @GetMapping("/list/apoderados/select")
    public  ResponseEntity<List<ApoderadoDetailsSelectDto>> listApoderadosSelect(@RequestParam(required = false) String nombre){
        return ResponseEntity.ok(atencionApoderadoService.getApoderadoSelect(nombre));
    }

    @GetMapping("/list/apoderado/{id}")
    public ResponseEntity<Page<AtenApoderadoListDetailDto>> listAtenApoderadoDetails( @PathVariable int id,
                                                                                      @RequestParam(required = false)
                                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                      LocalDate fecha,
                                                                                      @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(atencionApoderadoService.listAtenAPoderadoDetails(id, fecha, pageable ));
    }

    @GetMapping("/apoderado/{id}")
    public ResponseEntity<AtenApoderadoDto> getAtencionApoderado(@PathVariable int id){
        return ResponseEntity.ok(atencionApoderadoService.getAtencionApoderado(id));
    }

}
