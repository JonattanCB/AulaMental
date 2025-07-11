package com.abs.aulamental.repository;

import com.abs.aulamental.model.Asignar;
import com.abs.aulamental.model.enums.EstadoDocumento;
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
public interface AsignarRepository extends JpaRepository<Asignar, Integer> {

    @Query("""
           SELECT a FROM asignar  a WHERE a.estado != 'CERRADO' AND
           a.Practicante.id = :id
            ORDER BY
                   CASE a.estado
                       WHEN 'PENDIENTE' THEN 1
                       WHEN 'REVISADO' THEN 2
                       ELSE 3
                   END
           """)
    Page<Asignar> listAsignarOptionNombre(int id, Pageable pageable);

    @Query("""
    SELECT a FROM asignar a 
    WHERE a.Practicante.id = :id 
      AND (:fecha IS NULL OR a.FechaCreacion = :fecha)
    ORDER BY 
      CASE a.estado
        WHEN 'PENDIENTE' THEN 1
        WHEN 'REVISADO' THEN 2
        WHEN 'ENVIADO' THEN 3
        WHEN 'CERRADO' THEN 4
        ELSE 5
      END
    """)
    Page<Asignar> listAsignarPracticanteOptionFecha(int id, LocalDate fecha, Pageable pageable);

    @Query("""
            SELECT COUNT(a)
            FROM asignar a
            WHERE a.Practicante.id = :idPracticante
            AND a.estado = :estado
        """)
    long contarPorEstadoYPracticante(@Param("idPracticante") int idPracticante,
                                     @Param("estado") EstadoDocumento estado);

    @Query("""
            SELECT COUNT(a)
            FROM asignar a
            WHERE a.Usuario.id = :idPsicologo
            AND a.estado = :estado
        """)
    long contarPorEstadoYPsicologo(@Param("idPsicologo") int idPsicologo,
                                     @Param("estado") EstadoDocumento estado);

    @Query("""
        SELECT COUNT(a)
        FROM asignar a
        WHERE a.Usuario.id = :idPsicologo
        AND a.estado = :estado
        AND a.FechaCreacion BETWEEN :fechaInicio AND :fechaFin
    """)
    long contarEstadoYPsicologoEnMesActual(
            @Param("idPsicologo") int idPsicologo,
            @Param("estado") EstadoDocumento estado,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    @Query("""
        SELECT COUNT(a)
        FROM asignar a
        WHERE a.Usuario.id = :idPsicologo
        AND a.estado = :estado
    """)
    long contarEstadoYPsicologoTotal(
            @Param("idPsicologo") int idPsicologo,
            @Param("estado") EstadoDocumento estado
    );



    @Query("""
        SELECT COUNT(a)
        FROM asignar a
        WHERE a.Usuario.id = :idPsicologo
          AND a.Practicante.id <> :idPsicologo
    """)
    Long contarAsignacionesPorPsicologo(@Param("idPsicologo") int idPsicologo);

    @Query("""
    SELECT MAX(a.FechaCreacion) FROM asignar a
    WHERE a.Practicante.id = :idPracticante
""")
    Date obtenerUltimaFechaAsignacion(int idPracticante);


    Asignar searchAsignarsById(int id);

    @Query("""
        SELECT a
        FROM asignar a
        JOIN a.Usuario u
        JOIN u.usuarioRoles ur
        JOIN ur.rol r
        WHERE a.estado = 'ENVIADO'
          AND r.id = 1
          AND ur.estado = 'ACTIVO'
    """)
    List<Asignar> listarTareasEnviadasDePsicologos();

    @Query("""
        SELECT a
        FROM asignar a
        WHERE a.Practicante.id = :idPracticante
          AND a.estado IN (:estadoPendiente, :estadoRechazado)
        ORDER BY
            CASE 
                WHEN a.estado = :estadoPendiente THEN 0
                WHEN a.estado = :estadoRechazado THEN 1
                ELSE 2
            END
    """)
    List<Asignar> listarPorEstadosYPracticante(
            @Param("idPracticante") int idPracticante,
            @Param("estadoPendiente") EstadoDocumento estadoPendiente,
            @Param("estadoRechazado") EstadoDocumento estadoRechazado
    );
}
