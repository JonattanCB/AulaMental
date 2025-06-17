package com.abs.aulamental.repository;

import com.abs.aulamental.model.Rol;
import com.abs.aulamental.model.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    boolean existsByEstado(Estado estado);

    Rol getRolByNombre(String nombre);
}
