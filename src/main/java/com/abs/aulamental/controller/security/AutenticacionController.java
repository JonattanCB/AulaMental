package com.abs.aulamental.controller.security;

import com.abs.aulamental.dto.security.UsuarioLoginDto;
import com.abs.aulamental.dto.security.TokenDto;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.UsuarioRol;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.service.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        String nombre =   PersonaMapper.toConcatNombre(((Usuario) authentication.getPrincipal()).getPersona());
        String correo =  ((Usuario) authentication.getPrincipal()).getCorreo();
        String alias =     PersonaMapper.obtenerIniciales(((Usuario) authentication.getPrincipal()).getPersona());
        List<String> roles= ((Usuario) authentication.getPrincipal()).getUsuarioRoles().stream()
                .filter(usuarioRol -> usuarioRol.getEstado() == Estado.ACTIVO)
                .filter(usuarioRol -> usuarioRol.getRol().getEstado() == Estado.ACTIVO)
                .map(usuarioRol -> usuarioRol.getRol().getNombre()).toList();

        return ResponseEntity.ok(new TokenDto(((Usuario) authentication.getPrincipal()).getId(),nombre, token, correo, alias,roles));
    }
}
