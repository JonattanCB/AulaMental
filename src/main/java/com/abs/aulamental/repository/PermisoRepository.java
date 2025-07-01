package com.abs.aulamental.repository;

import com.abs.aulamental.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    @Query("""
        SELECT p FROM Permiso p
        WHERE p.rol.id IN :idsRol
        AND p.estado = 'ACTIVO'
        """)
    List<Permiso> findByRoles(List<Integer> idsRol);



}
