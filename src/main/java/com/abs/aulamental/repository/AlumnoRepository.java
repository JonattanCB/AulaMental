package com.abs.aulamental.repository;

import com.abs.aulamental.model.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    Alumno searchAlumnosById(int id);

    @Query("""
           SELECT a FROM alumno a WHERE
           (:nombre IS NULL OR LOWER(CONCAT(a.persona.nombre, ' ', a.persona.apaterno, ' ', a.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%')))
           """)
    Page<Alumno> getAlumnosOptionNombre(String nombre, Pageable pageable);

    @Query("""
           SELECT a FROM alumno a WHERE
           (:nombre IS NULL OR LOWER(CONCAT(a.persona.nombre, ' ', a.persona.apaterno, ' ', a.persona.amaterno)) LIKE CONCAT('%', LOWER(:nombre), '%'))
           """)
    List<Alumno> getAlumnosOptionNombretoList(String nombre);

    @Query("""
           SELECT a FROM alumno  a WHERE
           (:nombre IS NULL OR LOWER(CONCAT(a.persona.nombre, ' ', a.persona.apaterno, ' ', a.persona.amaterno)) LIKE CONCAT('%', LOWER(:nombre), '%'))
           """)
    Page<Alumno> getAlumnosOptionNombretoPage(String nombre, Pageable pageable);

    @Query("SELECT COUNT(a) FROM alumno a")
    long contarTotalAlumnos();
}
