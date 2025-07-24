package com.abs.aulamental.repository;

import com.abs.aulamental.model.Apoderado;
import com.abs.aulamental.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ApoderadoRepository extends JpaRepository<Apoderado, Integer> {
    @Query("""
            SELECT a FROM apoderado  a
            WHERE a.alumno.id = :idAlumno
            ORDER BY a.id asc
            """)
    List<Apoderado> searchApoderadoByAlumno(int idAlumno);

    @Query("""
        SELECT a FROM apoderado a
        WHERE a.id IN (
            SELECT MIN(aa.id) FROM apoderado aa
            WHERE (:nombre IS NULL OR LOWER(CONCAT(aa.persona.nombre, ' ', aa.persona.apaterno, ' ', aa.persona.amaterno)) LIKE CONCAT('%', LOWER(:nombre), '%'))
            GROUP BY aa.persona.id
        )
    """)
    List<Apoderado> listarApoderadoOptionNombre(String nombre);

    @Query("""
        SELECT a FROM apoderado a
        WHERE (:nombre IS NULL OR LOWER(CONCAT(a.persona.nombre, ' ', a.persona.apaterno, ' ', a.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%')))
            AND a.id IN (
                    SELECT MIN(a2.id)
                    FROM apoderado a2
                    GROUP BY a2.persona.id
                )
    """)
    Page<Apoderado> findbyApoderadosOpcionalnombre(String nombre, Pageable pageable);

    boolean existsByPersonaIdAndAlumnoId(Integer personaId, Integer alumnoId);

    Apoderado searchApoderadoById(int id);

    List<Apoderado> searchApoderadoByAlumno_Id(int alumnoId);

    List<Apoderado> findByAlumnoId(int alumnoId);
}
