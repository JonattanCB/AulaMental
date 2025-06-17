package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.EstadoAsistencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;

@Table(name = "asistencia")
@Entity(name = "asistencia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date fecha;

    @Column(name = "horaingreso")
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private EstadoAsistencia estado;

    private Timestamp fmodificacion;

    private Timestamp fregistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

}
