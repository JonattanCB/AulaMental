package com.abs.aulamental.repository;

import com.abs.aulamental.model.Asistencia;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.EstadoAsistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface
AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    long countByUsuarioIdAndEstado(int userId, EstadoAsistencia estado);

    @Query(value =
            "SELECT fecha " +
                    "FROM asistencia " +
                    "WHERE idusuario = :idUsuario " +
                    "ORDER BY fecha DESC " +
                    "LIMIT 1",
            nativeQuery = true)
    Date findUltimaFechaByUsuarioIdNative(@Param("idUsuario") int idUsuario);


    @Query("""
           SELECT a FROM asistencia a
           WHERE a.usuario.id = :idUsuario
           AND (:fecha IS NULL OR a.fecha >= :fecha)
           """)
    Page<Asistencia> findByUsuarioAndOptionalFecha(@Param("idUsuario") int idUsuario,@Param("fecha") LocalDate fecha,Pageable pageable
    );

    boolean existsAsistenciaByUsuarioIdAndFecha(int usuarioId, Date fecha);


    @Query("""
        SELECT COUNT(a)
        FROM asistencia a
        JOIN a.usuario u
        JOIN u.usuarioRoles ur
        WHERE a.fecha = CURRENT_DATE
          AND ur.rol.id = 2
          AND ur.estado = 'ACTIVO'
    """)
    long contarAsistenciasPracticantesHoy();


    @Query("""
    SELECT COUNT(a)
    FROM asistencia a
    WHERE a.fecha = CURRENT_DATE
""")
    long contarTotalAsistenciasHoy();

    @Query("""
        SELECT a
        FROM asistencia a
        JOIN FETCH a.usuario
        WHERE a.fecha = CURRENT_DATE
    """)
    List<Asistencia> listarAsistenciasHoy();


    @Query("""
        SELECT a
        FROM asistencia a
        JOIN a.usuario u
        JOIN u.usuarioRoles ur
        JOIN ur.rol r
        WHERE a.fecha = CURRENT_DATE
          AND r.id = 2
          AND ur.estado = 'ACTIVO'
    """)
    List<Asistencia> listarAsistenciasHoyDePracticantes();


}
