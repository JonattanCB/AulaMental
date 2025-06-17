package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Dias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalTime;

@Entity(name = "horario")
@Table(name = "horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Dias dia;

    @Column(name = "horaingreso")
    private LocalTime hora;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

}
