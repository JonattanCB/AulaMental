package com.abs.aulamental.controller.security;

import com.abs.aulamental.dto.security.UsuarioLoginDto;
import com.abs.aulamental.dto.security.TokenDto;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.service.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacionController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<TokenDto> autenticar(@RequestBody UsuarioLoginDto dto) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.correo(),
                        dto.contrasenia()
                )
        );
        String token = tokenService.generateTocken((Usuario) authentication.getPrincipal());
        if (token == null) {
            throw new ValidarExcepciones("Error al generar el token");
        }
        return ResponseEntity.ok(new TokenDto(((Usuario) authentication.getPrincipal()).getId(),token));
    }
}
