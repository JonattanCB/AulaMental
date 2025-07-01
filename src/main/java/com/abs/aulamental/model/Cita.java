package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.EstadoCitas;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "cita")
@Table(name = "cita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idalumno", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpsicologo", nullable = false)
    private Usuario psicologo;

    private String motivo;

    @Enumerated(EnumType.STRING)
    private EstadoCitas estado;

    private String observaciones;


    public void ActualizarEstado(EstadoCitas nuevoEstado, String observaciones) {
        this.estado = nuevoEstado;
        this.observaciones = observaciones;
    }

}
