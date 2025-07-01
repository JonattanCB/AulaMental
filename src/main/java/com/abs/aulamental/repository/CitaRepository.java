package com.abs.aulamental.repository;

import com.abs.aulamental.model.Cita;
import com.abs.aulamental.model.enums.EstadoCitas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPsicologoIdAndFecha(Integer idPsicologo, LocalDate fecha);

    @Query(
            value = """
        SELECT * FROM cita
        WHERE idalumno = :idAlumno
        ORDER BY CASE estado
            WHEN 'PENDIENTE' THEN 1
            WHEN 'CONFIRMADA' THEN 2
            WHEN 'CANCELADA' THEN 3
        END
        """,
            countQuery = """
        SELECT COUNT(*) FROM cita
        WHERE idalumno = :idAlumno
        """,
            nativeQuery = true
    )
    Page<Cita> findByAlumnoIdOrderByEstadoPriority(
            @Param("idAlumno") int idAlumno,
            Pageable pageable
    );

    @Query(
            value = """
        SELECT * FROM cita
        WHERE idpsicologo = :idPsicologo
        ORDER BY CASE estado
            WHEN 'PENDIENTE' THEN 1
            WHEN 'CONFIRMADA' THEN 2
            WHEN 'CANCELADA' THEN 3
        END
        """,
            countQuery = """
        SELECT COUNT(*) FROM cita
        WHERE idpsicologo = :idPsicologo
        """,
            nativeQuery = true
    )
    Page<Cita> findByPsicologoIdOrderByEstadoPriority(
            @Param("idPsicologo") int idPsicologo,
            Pageable pageable
    );

    long countByPsicologoIdAndEstado(Integer idPsicologo, EstadoCitas estado);

}
