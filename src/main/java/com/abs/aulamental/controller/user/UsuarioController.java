package com.abs.aulamental.controller.user;

import com.abs.aulamental.dto.permisos.PermisoMenuDto;
import com.abs.aulamental.dto.user.*;
import com.abs.aulamental.service.user.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    
    @GetMapping("/list/{id}")
    public ResponseEntity<Page<UsuarioListDto>> listUsers(@PathVariable int id, @RequestParam(required = false) String nombre, @PageableDefault(size = 10) Pageable pageable){
       return ResponseEntity.ok( usuarioService.listUser(id, nombre, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUser(@PathVariable int id){
        return ResponseEntity.ok(usuarioService.getUser(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UsuarioDetailDto> createUsuario(@RequestBody @Valid UsuarioCreateDto dto) {
        return ResponseEntity.ok(usuarioService.createUser(dto));
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<UsuarioDetailDto> updateUsuario(@RequestBody @Valid UsuarioUpdateDto dto) {
        return  ResponseEntity.ok(usuarioService.updateUser(dto));
    }

    @PutMapping("/update/estado/{id}")
    @Transactional
    public ResponseEntity<UsuarioEstadoUpdateDto> updateEstadoUsuario(@PathVariable int id){
        return ResponseEntity.ok(usuarioService.changerEstado(id));
    }


    @PutMapping("/update/contraseña/{id}")
    @Transactional
    public ResponseEntity<String> updateContraseñaUsuario(@PathVariable int id, @RequestBody @Valid UsuarioContraseñaDto dto) {
        return ResponseEntity.ok(usuarioService.updatePassword(id, dto));
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<PermisoMenuDto>> obtenerMenuUsuario(@PathVariable int id) {
        return ResponseEntity.ok(usuarioService.construirMenuParaUsuario(id));
    }
}
