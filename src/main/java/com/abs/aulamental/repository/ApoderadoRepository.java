package com.abs.aulamental.repository;

import com.abs.aulamental.model.Apoderado;
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
    Apoderado searchApoderadoByAlumno(int idAlumno);

    @Query("""
        SELECT a FROM apoderado  a WHERE
          (:nombre IS NULL OR LOWER(CONCAT(a.persona.nombre, ' ', a.persona.apaterno, ' ', a.persona.amaterno))  LIKE CONCAT('%', LOWER(:nombre), '%'))
                """)
    List<Apoderado> listarApoderadoOptionNombre(String nombre);

    @Query("""
        SELECT a FROM apoderado  a WHERE
          (:nombre IS NULL OR LOWER(CONCAT(a.persona.nombre, ' ', a.persona.apaterno, ' ', a.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%')))
                """)
    Page<Apoderado> findbyApoderadosOpcionalnombre(String nombre, Pageable pageable);

    Apoderado searchApoderadoById(int id);

    List<Apoderado> searchApoderadoByAlumno_Id(int alumnoId);
}
