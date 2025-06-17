package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "PermisoRol")
@Entity(name = "PermisoRol")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PermisoRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPermiso", nullable = false)
    private Permiso permiso;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
