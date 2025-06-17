package com.abs.aulamental.model;

import com.abs.aulamental.dto.atencionalumno.AtenAlumnoUpdateDto;
import com.abs.aulamental.utils.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "atencionalumnos")
@Table(name = "atencionalumnos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtencionAlumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idalumno", referencedColumnName = "id")
    private  Alumno alumno;

    private String motivo;

    private String resumen;

    private String conclusion;

    private String recomendacion;

    private String tecnicas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddiagnostico", referencedColumnName = "id")
    private Diagnostico diagnostico;

    private String comentario;

    private Date fecha;

    private Timestamp fregistro;

    private  Timestamp fmodificacion;

    public void ActualizarDatos(AtenAlumnoUpdateDto dto, Diagnostico diagnostico) {
        if( dto.motivo() != null && !dto.motivo().isEmpty() && !dto.motivo().equals(this.motivo)){
            this.motivo = dto.motivo();
        }

        if( dto.resumen() != null && !dto.resumen().isEmpty() && !dto.resumen().equals(this.resumen)){
            this.resumen = dto.resumen();
        }

        if( dto.conclusion() != null && !dto.conclusion().isEmpty() && !dto.conclusion().equals(this.conclusion)){
            this.conclusion = dto.conclusion();
        }

        if( dto.recomendacion() != null && !dto.recomendacion().isEmpty() && !dto.recomendacion().equals(this.recomendacion)){
            this.recomendacion = dto.recomendacion();
        }

        if( dto.tecnicas() != null && !dto.tecnicas().isEmpty() && !dto.tecnicas().equals(this.tecnicas)){
            this.tecnicas = dto.tecnicas();
        }

        if( dto.comentario() != null && !dto.comentario().isEmpty() && !dto.comentario().equals(this.comentario)){
            this.comentario = dto.comentario();
        }

        this.diagnostico = diagnostico;

        this.fmodificacion = DateUtil.nowTimestamp();
    }

}
