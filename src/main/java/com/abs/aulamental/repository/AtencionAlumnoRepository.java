package com.abs.aulamental.repository;

import com.abs.aulamental.model.AtencionAlumno;
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
public interface AtencionAlumnoRepository extends JpaRepository<AtencionAlumno, Integer> {

    @Query("""
    SELECT COUNT(a)
    FROM atencionalumnos a
    JOIN asignar asg ON asg.idDocumento = a.id
    WHERE a.alumno.id = :alumnoId
      AND asg.estado = 'CERRADO'
      AND asg.tdocumento = 'ATENCIONALUMNO'
    """)
    long countByAlumnoIdAndEstadoCerradoAndTipoDocumentoAtencionAlumno(@Param("alumnoId") Integer alumnoId);



    @Query("""
    SELECT MAX(a.fecha)
    FROM atencionalumnos a
    JOIN asignar asg ON asg.idDocumento = a.id
    WHERE a.alumno.id = :alumnoId
      AND asg.estado = 'CERRADO'
      AND asg.tdocumento = 'ATENCIONALUMNO'
    """)
    Date findUltimaFechaAtencionByAlumnoCerrada(@Param("alumnoId") Integer alumnoId);



    @Query("""
        SELECT a FROM atencionalumnos a
        JOIN asignar asg ON asg.idDocumento = a.id
        WHERE  asg.tdocumento = 'ATENCIONALUMNO'
            AND a.alumno.id = :alumnoId
        AND (:fecha IS NULL OR a.fecha >= :fecha)
        AND asg.estado = 'CERRADO'
    """)
    Page<AtencionAlumno> getAtencionesByAlumnoIdOptionFecha(
            @Param("alumnoId") int alumnoId,
            @Param("fecha") LocalDate fecha,
            Pageable pageable
    );



    @Query(value = """
        SELECT d.nombre, COUNT(a.id) AS total
        FROM atencionalumnos a
        JOIN diagnostico d ON a.iddiagnostico = d.id
        JOIN asignar s ON s.id_documento = a.id
        WHERE s.estado = 'CERRADO' 
          AND s.tdocumento = 'ATENCIONALUMNO'
        GROUP BY d.nombre
        ORDER BY total DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> obtenerTop5DiagnosticosUsados();

    @Query("""
        SELECT a FROM atencionalumnos a
        WHERE a.id = :id
    """)
    AtencionAlumno searchAtencionAlumnoById(int id);

    @Query("""
        SELECT a FROM atencionalumnos a
        WHERE FUNCTION('MONTH', a.fecha) = :mes
          AND FUNCTION('YEAR', a.fecha) = :anio
          AND EXISTS (
              SELECT 1 FROM asignar asig
              WHERE asig.idDocumento = a.id
                AND asig.tdocumento = 'ATENCIONALUMNO'
                AND asig.estado = 'CERRADO'
          )
    """)
    List<AtencionAlumno> findByMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);

    @Query("""
        SELECT a FROM atencionalumnos a
        WHERE a.alumno.id = :idAlumno
          AND EXISTS (
            SELECT 1 FROM asignar asig
            WHERE asig.idDocumento = a.id
              AND asig.tdocumento = 'ATENCIONALUMNO'
              AND asig.estado = 'CERRADO'
        )
    """)
    List<AtencionAlumno> findAtencionesAlumnosCerradasByAlumnoId(@Param("idAlumno") int idAlumno);

}
