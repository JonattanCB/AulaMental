package com.abs.aulamental.controller.user;

import com.abs.aulamental.external.reniec.dto.RenicDto;
import com.abs.aulamental.service.user.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persona")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

    @GetMapping("/{dni}")
    public ResponseEntity<RenicDto> obtenerPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(personaService.getPersonaPorDni(dni));
    }

}
