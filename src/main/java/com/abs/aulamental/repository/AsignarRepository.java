package com.abs.aulamental.repository;

import com.abs.aulamental.dto.asignar.AsignarListPracticantesDto;
import com.abs.aulamental.dto.asignar.AsignarTaskListDto;
import com.abs.aulamental.model.Asignar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface AsignarRepository extends JpaRepository<Asignar, Integer> {

    @Query("""
           SELECT a FROM asignar  a WHERE a.estado != 'CERRADO' AND
           (:nombre IS NULL OR LOWER(CONCAT(a.Practicante.persona.nombre, ' ', a.Practicante.persona.apaterno, ' ', a.Practicante.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%')))
           """)
    Page<Asignar> listAsignarOptionNombre(String nombre, Pageable pageable);

    @Query("""
           SELECT a FROM asignar  a WHERE a.Practicante.id = :id AND
           (:fecha IS NULL OR a.FechaCreacion = :fecha)
           ORDER BY  CASE WHEN a.estado like 'PENDIENTE%' THEN 0 ELSE 1 END, a.estado
           """)
    Page<Asignar> listAsignarPracticanteOptionFecha(int id, Date fecha, Pageable pageable);

    Asignar searchAsignarsById(int id);
}
