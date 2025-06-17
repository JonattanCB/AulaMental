package com.abs.aulamental.repository;

import com.abs.aulamental.model.Horario;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.Dias;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM horario h WHERE h.usuario.id = :usuarioId")
    int deleteAllByUsuarioId(@Param("usuarioId") Integer usuarioId);

    List<Horario> findAllByUsuarioId(int usuarioId);

    Optional<Horario> findByUsuarioAndDia(Usuario usuario, Dias dia);

    Usuario usuario(Usuario usuario);
}
