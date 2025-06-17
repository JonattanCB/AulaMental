package com.abs.aulamental.repository;

import com.abs.aulamental.model.itemSucesos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ItemSucesoRepository extends JpaRepository<itemSucesos, Integer> {
    @Query("SELECT i from  item_sucesos  i where i.alumno.id = :alumnoId and " +
            "(:fecha IS NULL OR i.sucesos.fecha = :fecha) and "+
            "(:nombre IS NULL OR i.sucesos.nombre LIKE %:nombre%)")
    Page<itemSucesos> findByIdUsuarioandOptionalFecha(int alumnoId, String nombre, Date fecha, Pageable pageable);


}
