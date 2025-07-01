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

@Repository
public interface AsignarRepository extends JpaRepository<Asignar, Integer> {

    @Query("""
           SELECT a FROM asignar  a WHERE a.estado != 'CERRADO' AND
           (:nombre IS NULL OR LOWER(CONCAT(a.Practicante.persona.nombre, ' ', a.Practicante.persona.apaterno, ' ', a.Practicante.persona.amaterno)) LIKE LOWER(CONCAT('%', :nombre, '%')))
           """)
    Page<Asignar> listAsignarOptionNombre(String nombre, Pageable pageable);

    @Query("""
       SELECT a FROM asignar a WHERE a.Practicante.id = :id AND
       (:fecha IS NULL OR a.FechaCreacion = :fecha)
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

    Asignar searchAsignarsById(int id);
}
