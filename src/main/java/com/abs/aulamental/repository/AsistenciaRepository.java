package com.abs.aulamental.repository;

import com.abs.aulamental.model.Asistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface
AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    @Query("""
           SELECT a FROM asistencia a
           WHERE a.usuario.id = :idUsuario
           AND (:fecha IS NULL OR a.fecha = :fecha)
           """)
    Page<Asistencia> findByUsuarioAndOptionalFecha(
            @Param("idUsuario") int idUsuario,
            @Param("fecha") Date fecha,
            Pageable pageable
    );

    boolean existsAsistenciaByUsuarioIdAndFecha(int usuarioId, Date fecha);

}
