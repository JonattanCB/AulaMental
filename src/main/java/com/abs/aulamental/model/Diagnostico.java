package com.abs.aulamental.model;

import com.abs.aulamental.dto.diagnostico.DiagnosticoUpdateDto;
import com.abs.aulamental.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "diagnostico")
@Entity(name = "diagnostico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public void ActualizarDatos(DiagnosticoUpdateDto dto) {
        if ( dto.nombre() != null && !dto.nombre().isEmpty() && !dto.nombre().equals(this.nombre)){
            this.nombre = dto.nombre();
        }

        if (dto.descripcion() != null && !dto.descripcion().isEmpty() && !dto.descripcion().equals(this.descripcion)) {
            this.descripcion = dto.descripcion();
        }

    }

    public void ActualizarEstado(Estado estado) {
        if (estado != null && !estado.equals(this.estado)) {
            this.estado = estado;
        }
    }


}