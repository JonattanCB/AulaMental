package com.abs.aulamental.repository;

import com.abs.aulamental.model.AtencionAlumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface AtencionAlumnoRepository extends JpaRepository<AtencionAlumno, Integer> {
    @Query("""
        SELECT a FROM atencionalumnos a
        JOIN asignar asg ON asg.idDocumento = a.id
        WHERE a.alumno.id = :alumnoId
        AND (:fecha IS NULL OR a.fecha = :fecha)
        AND asg.estado = 'CERRADO'
        """)
    Page<AtencionAlumno> getAtencionesByAlumnoIdOptionFecha(int alumnoId, Date fecha, Pageable pageable);

    @Query("""
        SELECT a FROM atencionalumnos a
        WHERE a.id = :id
    """)
    AtencionAlumno searchAtencionAlumnoById(int id);
}
