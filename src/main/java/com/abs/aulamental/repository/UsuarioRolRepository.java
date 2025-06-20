package com.abs.aulamental.repository;

import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.UsuarioRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    @Query("SELECT CASE WHEN COUNT(ur) > 0 THEN true ELSE false END FROM UsuarioRol ur WHERE ur.usuario.id = :idUsuario AND ur.rol.id = :idRol")
    boolean existsUsuarioRolByIdUsuarioAndIdRol(int idUsuario, int idRol);

    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario.id = :idUsuario AND ur.rol.id = :idRol")
    UsuarioRol findByUsuarioIdAndRolId(int idUsuario, int idRol);

}
