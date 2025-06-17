package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Permiso")
@Entity(name = "Permiso")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String url;

    private String label;

    private String icon;

    private int idparent;

    private Estado estado;

}
