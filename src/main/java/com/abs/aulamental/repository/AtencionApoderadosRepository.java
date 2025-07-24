package com.abs.aulamental.repository;

import com.abs.aulamental.model.AtencionApoderados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AtencionApoderadosRepository extends JpaRepository<AtencionApoderados, Integer> {
    AtencionApoderados getAtencionApoderadosById(Integer id);

    @Query("""
        SELECT aa FROM atencion_apoderados aa
        JOIN asignar asg ON asg.idDocumento = aa.id
        WHERE asg.tdocumento = 'ATENCIONAPODERADO'
          AND aa.apoderado.persona.id = :personaId
          AND (:fecha IS NULL OR aa.fecha >= :fecha)
          AND asg.estado = 'CERRADO'
    """)
    Page<AtencionApoderados> listAtencionApoderadoOptionalDate(int id, int personaId, LocalDate fecha, Pageable pageable);


    @Query("""
    SELECT COUNT(a)
    FROM atencion_apoderados a
    JOIN asignar asg ON asg.idDocumento = a.id
    WHERE a.apoderado.id = :idApoderado
      AND asg.estado = 'CERRADO'
      AND asg.tdocumento = 'ATENCIONAPODERADO'
    """)
    long contarAtencionesPorApoderadoCerradas(@Param("idApoderado") int idApoderado);


    @Query("""
    SELECT MAX(a.fecha)
    FROM atencion_apoderados a
    JOIN asignar asg ON asg.idDocumento = a.id
    WHERE a.apoderado.id = :idApoderado
      AND asg.estado = 'CERRADO'
      AND asg.tdocumento = 'ATENCIONAPODERADO'
    """)
    java.sql.Date obtenerUltimaFechaPorApoderadoCerrada(@Param("idApoderado") int idApoderado);

    AtencionApoderados searchAtencionApoderadosById(int idaap);

    @Query("""
    SELECT ap FROM atencion_apoderados ap
    WHERE FUNCTION('MONTH', ap.fecha) = :mes
      AND FUNCTION('YEAR', ap.fecha) = :anio
      AND EXISTS (
          SELECT 1 FROM asignar a
          WHERE a.idDocumento = ap.id
            AND a.tdocumento = 'ATENCIONAPODERADO'
            AND a.estado = 'CERRADO'
      )
""")
    List<AtencionApoderados> findByMesYAnio(@Param("mes") int mes, @Param("anio") int anio);

}
