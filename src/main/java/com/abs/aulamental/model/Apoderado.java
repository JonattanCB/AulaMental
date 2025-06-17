package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.model.enums.Parentesco;
import com.abs.aulamental.model.enums.SiNo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "apoderado")
@Entity(name = "apoderado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apoderado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "idalumno", referencedColumnName = "id")
    private Alumno alumno;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String ocupacion;

    @Enumerated(EnumType.STRING)
    private Parentesco parentesco;

    @Enumerated(EnumType.STRING)
    private SiNo convive;

}
