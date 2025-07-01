package com.abs.aulamental.repository;

import com.abs.aulamental.model.Sucesos;
import com.abs.aulamental.model.enums.EstadoAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucesoRepository extends JpaRepository<Sucesos, Integer> {

    Sucesos searchSucesosById(int id);
}
