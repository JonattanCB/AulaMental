package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.NivelGravedad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "item_sucesos")
@Entity(name = "item_sucesos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class itemSucesos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idsucesos", referencedColumnName = "id")
    private Sucesos sucesos;

    @ManyToOne
    @JoinColumn(name = "id_alumno", referencedColumnName = "id")
    private Alumno alumno;

    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_gravedad")
    private NivelGravedad nivelGravedad;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

}
