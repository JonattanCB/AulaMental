package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "UsuarioRol")
@Entity(name = "UsuarioRol")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Rol", nullable = false)
    private Rol rol;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public void changeEstado(Estado estado) {
        this.estado = estado;
    }

}
