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



    @Query("""
        SELECT ur.usuario 
        FROM UsuarioRol ur 
        WHERE ur.rol.id = :idRol 
        AND LOWER(CONCAT(ur.usuario.persona.nombre, ' ', ur.usuario.persona.apaterno,' ', ur.usuario.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%'))
    """)
    Page<Usuario> buscarUsuariosPorRolYNombre(
            @Param("idRol") int idRol,
            @Param("nombre") String nombre,
            Pageable pageable
    );


    @Query("SELECT u.usuario FROM UsuarioRol u WHERE u.rol.id = :id AND " +
            "(:nombre IS NULL OR LOWER(CONCAT(u.usuario.persona.nombre, ' ', u.usuario.persona.apaterno, ' ', u.usuario.persona.amaterno)) " +
            "LIKE LOWER(CONCAT('%', :nombre, '%')))")
    Page<Usuario> listarUsuarioAsistenciaOptionombrePracticante(@Param("nombre") String nombre,
                                                                @Param("id") int id,
                                                                Pageable pageable);
}
