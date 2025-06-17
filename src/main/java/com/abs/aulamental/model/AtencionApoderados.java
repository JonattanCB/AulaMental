package com.abs.aulamental.model;

import com.abs.aulamental.dto.atencionapoderado.AtenApoderadoUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "atencion_apoderados")
@Entity(name = "atencion_apoderados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtencionApoderados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apoderado_id")
    private Apoderado apoderado;

    private String motivo;

    private String resumen;

    private String conclusiones;

    private String recomendaciones;

    private String intervencion;

    private Date fecha;

    private String comentario;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    public void ActualizarDatos(AtenApoderadoUpdateDto dto){
        if (dto.motivo() != null && !dto.motivo().isEmpty() && !dto.motivo().equals(this.motivo)){
            this.motivo = dto.motivo();
        }

        if(dto.resumen() != null && !dto.resumen().isEmpty() && !dto.resumen().equals(this.resumen)){
            this.resumen = dto.resumen();
        }

        if (dto.conclusiones() != null && !dto.conclusiones().isEmpty() && !dto.conclusiones().equals(this.conclusiones)){
            this.conclusiones = dto.conclusiones();
        }

        if(dto.recomendaciones() !=null && !dto.recomendaciones().isEmpty() && !dto.recomendaciones().equals(this.recomendaciones)){
            this.recomendaciones = dto.recomendaciones();
        }

        if(dto.intervencion() != null && !dto.intervencion().isEmpty() && !dto.intervencion().equals(this.intervencion)){
            this.intervencion = dto.intervencion();
        }

        if (dto.comentario() != null && !dto.comentario().isEmpty() && !dto.comentario().equals(this.comentario)){
            this.comentario=dto.comentario();
        }

        this.fmodificacion = new Timestamp(System.currentTimeMillis());
    }
    
}
