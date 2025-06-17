package com.abs.aulamental.model;

import com.abs.aulamental.dto.user.UsuarioUpdateDto;
import com.abs.aulamental.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "Persona")
@Entity(name = "Persona")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String apaterno;

    private String amaterno;

    private String tdocumento;

    private String ndocumento;

    private String telefono1;

    private String telefono2;

    @Column(name = "correo_personal")
    private String correoPersonal;

    private String direccion;

    private String lnacimiento;

    private String fnacimiento;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public void updatePersona (UsuarioUpdateDto dto){
        if( dto.telefono1() != null && !dto.telefono1().isEmpty() && !dto.telefono1().equals(this.telefono1)){
            this.telefono1 = dto.telefono1();
        }

        if ( dto.telefono2() != null && !dto.telefono2().isEmpty() && !dto.telefono2().equals(this.telefono2)){
            this.telefono2 = dto.telefono2();
        }

        if ( dto.correopersonal() != null && !dto.correopersonal().isEmpty() && !dto.correopersonal().equals(this.correoPersonal)){
            this.correoPersonal = dto.correopersonal();
        }

        if (dto.direccion() != null && !dto.direccion().isEmpty() && !dto.direccion().equals(this.direccion)){
            this.direccion = dto.direccion();
        }

        this.fmodificacion = Timestamp.valueOf(java.time.LocalDateTime.now());
    }

    public void actualizarEstado(Estado estado){
        if (estado != null && !estado.equals(this.estado)){
            this.estado = estado;
        }
        this.fmodificacion = Timestamp.valueOf(java.time.LocalDateTime.now());
    }


}
