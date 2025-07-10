package com.abs.aulamental.repository;

import com.abs.aulamental.model.Sucesos;
import com.abs.aulamental.model.enums.EstadoAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;

@Repository
public interface SucesoRepository extends JpaRepository<Sucesos, Integer> {

    Sucesos searchSucesosById(int id);

    @Query("""
        SELECT COUNT(s)
        FROM sucesos s
        WHERE s.usuario.id = :idUsuario
    """)
    long contarTotalSucesosPorUsuario(@Param("idUsuario") int idUsuario);


    @Query("""
        SELECT COUNT(s)
        FROM sucesos s
        WHERE  s.fregistro BETWEEN :fechaInicio AND :fechaFin
    """)
    long contarSucesosPorUsuarioEnSemanaActual(
            @Param("fechaInicio") Timestamp fechaInicio,
            @Param("fechaFin") Timestamp fechaFin
    );

    Timestamp fecha(Date fecha);

    @Query("""
        SELECT COUNT(DISTINCT i.alumno.id)
        FROM item_sucesos i
    """)
    long contarAlumnosConSucesos();


}
