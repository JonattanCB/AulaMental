package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.model.enums.Nivel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "alumno")
@Entity(name = "alumno")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    private Persona persona;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    private int grado;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apoderado> apoderados = new ArrayList<>();

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<itemSucesos> sucesos = new ArrayList<>();

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AtencionAlumno> atenciones = new ArrayList<>();

}
