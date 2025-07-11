package com.abs.aulamental.repository;

import com.abs.aulamental.model.Rol;
import com.abs.aulamental.model.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    boolean existsByEstado(Estado estado);

    @Query("""
            SELECT r FROM Rol r where r.nombre=:nombre
            """)
    List<Rol> listRolbyRols(String nombre);

    Rol getRolByNombre(String nombre);

}
