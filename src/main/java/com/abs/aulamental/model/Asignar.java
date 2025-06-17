package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table(name = "asignar")
@Entity(name = "asignar")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asignar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario Usuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_practicante", nullable = false)
    private Usuario Practicante;

    @Enumerated(EnumType.STRING)
    private Tipodocumentacion tdocumento;

    @Column(name = "id_documento", nullable = false)
    private int idDocumento;

    @Enumerated(EnumType.STRING)
    private EstadoDocumento estado;

    private String observaciones;

    @Column(name = "fecha_creacion")
    private Date FechaCreacion;

    public void ActualizarEstado(EstadoDocumento estado) {
        this.estado = estado;
    }

    public void ActualizarObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
