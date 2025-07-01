package com.abs.aulamental.repository;

import com.abs.aulamental.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreo(String email);

    Usuario searchUsuarioById(int id);

    boolean existsByCorreo(String correo);

    @Query("""
        SELECT ur.usuario FROM UsuarioRol ur where 
          ur.rol.nombre = "Practicante" and
         (:nombre is NULL OR  LOWER(CONCAT(ur.usuario.persona.nombre, ' ', ur.usuario.persona.apaterno, ' ', ur.usuario.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%')) ) 
        """)
    List<Usuario> listUsuarioPracticante(String nombre);

    @Query("""
        SELECT ur.usuario FROM UsuarioRol ur WHERE 
        (:nameRol IS NULL OR ur.rol.nombre = :nameRol) AND 
        (:name IS NULL OR LOWER(CONCAT(ur.usuario.persona.nombre, ' ', ur.usuario.persona.apaterno, ' ', ur.usuario.persona.amaterno)) LIKE LOWER(CONCAT('%', :name, '%')))
        order by ur.usuario.id asc 
        """)
    Page<Usuario> listUserOptionalNombre( String name,String nameRol, Pageable pageable);

    @Query("""
            SELECT ur.usuario FROM UsuarioRol ur WHERE
            (:nombre IS NULL OR LOWER(CONCAT(ur.usuario.persona.nombre, ' ', ur.usuario.persona.apaterno, ' ', ur.usuario.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND
            ur.rol.nombre='Psicologia'
        """)
    List<Usuario> listUsuarioByPsicologoAndNombre(String nombre);

}
