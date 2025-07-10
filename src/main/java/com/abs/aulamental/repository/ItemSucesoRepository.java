package com.abs.aulamental.repository;

import com.abs.aulamental.model.enums.EstadoAsistencia;
import com.abs.aulamental.model.enums.NivelGravedad;
import com.abs.aulamental.model.itemSucesos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemSucesoRepository extends JpaRepository<itemSucesos, Integer> {

    long countByAlumnoIdAndNivelGravedad(int userId, NivelGravedad nivel);

    @Query("SELECT i from  item_sucesos  i where i.alumno.id = :alumnoId and " +
            "(:nombre IS NULL OR LOWER(i.sucesos.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    Page<itemSucesos> findByIdUsuarioandOptionalFecha(int alumnoId, String nombre, Pageable pageable);

    @Query("""
    SELECT i
    FROM item_sucesos i
    WHERE MONTH(i.fregistro) = MONTH(CURRENT_DATE)
      AND YEAR(i.fregistro) = YEAR(CURRENT_DATE)
""")
    List<itemSucesos> listarItemSucesosDelMesActual();

    @Query("""
        SELECT COUNT(i)
        FROM item_sucesos i
        WHERE MONTH(i.fregistro) = MONTH(CURRENT_DATE)
          AND YEAR(i.fregistro) = YEAR(CURRENT_DATE)
          AND i.nivelGravedad = :nivelGravedad
    """)
    long contarTotalItemSucesosDelMesPorGravedad(@Param("nivelGravedad") NivelGravedad nivelGravedad);

    @Query("""
    SELECT COUNT(DISTINCT i.alumno.id)
    FROM item_sucesos i
    WHERE MONTH(i.fregistro) = MONTH(CURRENT_DATE)
      AND YEAR(i.fregistro) = YEAR(CURRENT_DATE)
""")
    long contarAlumnosUnicosEnItemSucesosDelMes();

}
