package com.abs.aulamental.controller.asistencia;

import com.abs.aulamental.dto.asistencia.AsistenciaDetailDto;
import com.abs.aulamental.dto.asistencia.AsistenciaDto;
import com.abs.aulamental.dto.asistencia.AsistenciaUsuarioListDto;
import com.abs.aulamental.service.asistencia.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/asistencia")
@RequiredArgsConstructor
public class AsistenciaController {
    private final AsistenciaService asistenciaService;

    @PostMapping("/create/{id}")
    public ResponseEntity<AsistenciaDetailDto> createAsis(@PathVariable int id) {
        return ResponseEntity.ok(asistenciaService.createAsistencia(id));
    }

    @GetMapping("/list/{idcreador}")
    public ResponseEntity<Page<AsistenciaUsuarioListDto>> listAsis(@PathVariable int idcreador, @RequestParam(required = false) String nombre, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(asistenciaService.listAsistencias(idcreador, nombre,pageable));
    }

    @GetMapping("/list/usuario/{idUsuario}")
    public ResponseEntity<Page<AsistenciaDto>> listAsisUser(@PathVariable int idUsuario,@RequestParam(required = false) Date fecha,Pageable pageable){
        return ResponseEntity.ok(asistenciaService.listAsistenciasUser(idUsuario,fecha,pageable));
    }



}
