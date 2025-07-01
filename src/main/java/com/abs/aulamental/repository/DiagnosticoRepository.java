package com.abs.aulamental.repository;

import com.abs.aulamental.model.Diagnostico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Integer> {

    boolean existsByNombre(String nombre);

    @Query("""
           SELECT d FROM diagnostico d where (:nombre IS NULL OR d.nombre like CONCAT('%', :nombre, '%'))
           """)
    Page<Diagnostico> findOpcionalbyNombre(String nombre, Pageable pageable);

    @Query("""
           SELECT d FROM diagnostico d where (:nombre IS NULL OR d.nombre like CONCAT('%', :nombre, '%'))
           """)
    List<Diagnostico> ListfindOpcionalbyNombre(String nombre);

    Diagnostico searchDiagnosticoById(int id);

    boolean existsByNombreAndIdNot(String nombre, int id);
}
