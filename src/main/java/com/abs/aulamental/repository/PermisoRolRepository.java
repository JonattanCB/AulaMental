package com.abs.aulamental.repository;

import com.abs.aulamental.model.PermisoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRolRepository extends JpaRepository<PermisoRol, Integer> {

}
