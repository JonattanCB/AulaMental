package com.abs.aulamental.repository;

import com.abs.aulamental.model.enums.EstadoAsistencia;
import com.abs.aulamental.model.enums.NivelGravedad;
import com.abs.aulamental.model.itemSucesos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ItemSucesoRepository extends JpaRepository<itemSucesos, Integer> {

    long countByAlumnoIdAndNivelGravedad(int userId, NivelGravedad nivel);

    @Query("SELECT i from  item_sucesos  i where i.alumno.id = :alumnoId and " +
            "(:nombre IS NULL OR LOWER(i.sucesos.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    Page<itemSucesos> findByIdUsuarioandOptionalFecha(int alumnoId, String nombre, Pageable pageable);

}
