package com.abs.aulamental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "sucesos")
@Entity(name = "sucesos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucesos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private  Usuario usuario;

    private String nombre;

    private String detalles;

    private String argurmentosalumno;

    private String accionesrealizadas;

    private Date fecha;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @OneToMany(mappedBy = "sucesos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<itemSucesos> itemSucesos = new ArrayList<>();

}
