package com.abs.aulamental.service.rol;

import com.abs.aulamental.dto.rol.RolDto;
import com.abs.aulamental.mapper.RolMapper;
import com.abs.aulamental.model.Rol;
import com.abs.aulamental.model.UsuarioRol;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.repository.RolRepository;
import com.abs.aulamental.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    public List<RolDto> findAll() {
        List<Rol> rols = rolRepository.findAll();
        return rols.stream().map(RolMapper::toDto).toList();
    }

    public boolean hasRole(int idUsuario, String namerol){
        var rol = rolRepository.getRolByNombre(namerol);
        return usuarioRolRepository.existsUsuarioRolByIdUsuarioAndIdRol(idUsuario, rol.getId());
    }

    public void changeUserRoleStatus(int usuario, int rol, Estado estado) {
        var usuarioRol = usuarioRolRepository.findByUsuarioIdAndRolId(usuario, rol);
        if (usuarioRol != null) {
            usuarioRol.changeEstado(estado);
        }
    }

    public void createUserRol(UsuarioRol usuarioRol){
        usuarioRolRepository.save(usuarioRol);
    }

}
