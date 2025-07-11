package com.abs.aulamental.controller.user;

import com.abs.aulamental.dto.security.CambioContraseniaCodeDto;
import com.abs.aulamental.dto.security.CodeGeneradorDto;
import com.abs.aulamental.dto.security.RespuestaCodeDto;
import com.abs.aulamental.dto.security.validacionCodeDto;
import com.abs.aulamental.service.user.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class ChangePassworController {
    private final UsuarioService usuarioService;

    @GetMapping("/codeGenerador/{correo}")
    public ResponseEntity<CodeGeneradorDto> generarPassword(@PathVariable String correo) {
        return  ResponseEntity.ok(usuarioService.generedorCode(correo));
    }

    @PostMapping("/auth/validarcodigo")
    public ResponseEntity<RespuestaCodeDto> validacionCode(@RequestBody validacionCodeDto dto) {
        return ResponseEntity.ok(usuarioService.validacionCode(dto));
    }

    @PostMapping("/auth/cambiarContrasena")
    public ResponseEntity<RespuestaCodeDto> changerPassword(@RequestBody CambioContraseniaCodeDto dto){
        return ResponseEntity.ok(usuarioService.changerPasswordCode(dto));
    }

}
