package com.abs.aulamental.controller.asignacion;

import com.abs.aulamental.dto.asignar.*;
import com.abs.aulamental.dto.atencionalumno.AtenAlumnoDetailDto;
import com.abs.aulamental.dto.atencionalumno.AtenAlumnoUpdateDto;
import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoDetailDto;
import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoUpdateDto;
import com.abs.aulamental.dto.user.PracticanteListDetailsDto;
import com.abs.aulamental.service.asignar.AsignarService;
import jakarta.transaction.Transactional;
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
@RequestMapping("/api/asignar")
@RequiredArgsConstructor
public class AsignarController {
    private final AsignarService asignarService;

    @PostMapping("/create")
    public ResponseEntity<AsignarDetailsDto> createAsignar(@RequestBody AsignarCreateUserDto dto) {
        return ResponseEntity.ok(asignarService.createAsignar(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<AsignarListTablePracticanteDto>> listAsigPracticante(@RequestParam(required = false) String nombre, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(asignarService.listAsignarPracticantes(nombre, pageable));
    }

    @GetMapping("/list/psicologo/details/{id}")
    public ResponseEntity<AsignarPsicologoDetailsDto> detailsDtoAsigPsicologo(@PathVariable int id){
        return ResponseEntity.ok(asignarService.getPsicologoDetails(id));
    }

    @GetMapping("/list/task/{id}")
    public ResponseEntity<Page<AsignarListPracticantesDto>> listAsignar(@PathVariable int id, @PageableDefault(size = 10) Pageable pageable) {
        System.out.println(id);
        return ResponseEntity.ok(asignarService.listAsignar(id,pageable));
    }

    @GetMapping("/list/practicate")
    public  ResponseEntity<List<PracticanteListDetailsDto>> listPracticates(@RequestParam(required = false) String nombre){
        return  ResponseEntity.ok(asignarService.listPracticanteDetails(nombre));
    }

    @GetMapping("/practicante/list/{id}")
    public ResponseEntity<Page<AsignarTaskListDto>> listAsignacionPracticantes(@PathVariable int id,
                                                                               @RequestParam(required = false)
                                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate fecha,
                                                                               @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(asignarService.listAsignacionPracticantes(id,fecha,pageable));
    }

    @GetMapping("/practicante/practicante/details/{id}")
    public ResponseEntity<AsignarPracticanteDetailsDto> getPracticanteDetails(@PathVariable int id ){
        return ResponseEntity.ok(asignarService.getPracticanteDetails(id));
    }


    @PutMapping("/practicante/atencionAlumno")
    @Transactional
    public ResponseEntity<AtenAlumnoDetailDto> updateAtencionAlumno(@RequestBody AtenAlumnoUpdateDto dto){
        return ResponseEntity.ok(asignarService.updateAtencionAlumno(dto));
    }

    @PutMapping("/practicante/atencionApoderado")
    @Transactional
    public ResponseEntity<AtenApoderadoDetailDto> updateAtencionApoderado(@RequestBody AtenApoderadoUpdateDto dto){
        return ResponseEntity.ok(asignarService.updateAtencionApoderado(dto));
    }

    @PutMapping("/practicante/aprobado/{id}")
    @Transactional
    public ResponseEntity<AsignarDetailsDto> updateAsignarAprobado(@PathVariable int id){
        return ResponseEntity.ok(asignarService.updateAsignarAporbado(id));
    }

    @PutMapping("/practicante/rechazado/{id}")
    @Transactional
    public ResponseEntity<AsignarDetailsDto> updateAsignarRechazado(@PathVariable int id, @RequestParam String observacion){
        return ResponseEntity.ok(asignarService.updateAsignarRechazado(id,observacion));
    }


}