package com.abs.aulamental.repository;

import com.abs.aulamental.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonasRepository extends JpaRepository<Persona, Integer> {

    boolean existsPersonaByNdocumento(String ndocumento);

}
