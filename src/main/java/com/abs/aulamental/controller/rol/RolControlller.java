package com.abs.aulamental.controller.rol;

import com.abs.aulamental.dto.rol.RolDto;
import com.abs.aulamental.service.rol.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rol")
@RequiredArgsConstructor
public class RolControlller {
    private final RolService rolService;

    @GetMapping("/list/{id}")
    public ResponseEntity<List<RolDto>> findAll( @PathVariable int id) {
        return ResponseEntity.ok(rolService.findAll(id));
    }

}
