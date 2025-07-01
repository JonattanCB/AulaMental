package com.abs.aulamental.model;

import com.abs.aulamental.model.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    private Persona persona;

    private String correo;

    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Timestamp fregistro;

    private Timestamp fmodificacion;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<UsuarioRol> usuarioRoles;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Horario> horarios;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Asistencia> asistencias;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Sucesos> incidentes;

    public void actualizarEstado(Estado estado){
        if (estado != null && !estado.equals(this.estado)){
            this.estado = estado;
            this.getPersona().actualizarEstado(estado);
        }
        this.fmodificacion = Timestamp.valueOf(java.time.LocalDateTime.now());
    }

    public void actualizarContrasena(String contrasena) {
        if (contrasena != null && !contrasena.isEmpty() && !contrasena.equals(this.contrasena)) {
            this.contrasena = contrasena;
            this.fmodificacion = Timestamp.valueOf(java.time.LocalDateTime.now());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuarioRoles.stream()
                .map(usuarioRol -> new SimpleGrantedAuthority("ROLE_"+usuarioRol.getRol().getNombre())).toList();
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.estado==Estado.ACTIVO;
    }
}
