package com.abs.aulamental.repository;

import com.abs.aulamental.model.AtencionApoderados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AtencionApoderadosRepository extends JpaRepository<AtencionApoderados, Integer> {
    AtencionApoderados getAtencionApoderadosById(Integer id);

    @Query("""
            SELECT aa FROM atencion_apoderados aa WHERE aa.apoderado.id=:id
            AND (:fecha IS NULL or aa.fecha = :fecha )
            """)
    Page<AtencionApoderados> listAtencionApoderadoOptionalDate(int id, Date fecha, Pageable pageable);

    AtencionApoderados searchAtencionApoderadosById(int idaap);
}
