package com.abs.aulamental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private  final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        // Endpoints Privados
                        .requestMatchers("/api/**").authenticated()
                        // Endpoints Usuario
                        .requestMatchers("/api/usuario/**").hasAnyAuthority("ROLE_Directora", "ROLE_Psicologia")
                        // Endpoints Rol
                        .requestMatchers("/api/rol/**").hasAnyAuthority("ROLE_Directora", "ROLE_Psicologia")
                        // Endpoints Asistencia
                        .requestMatchers("/api/asistencia/create/**").authenticated()
                        .requestMatchers("/api/asistencia/list/**").hasAnyAuthority("ROLE_Directora", "ROLE_Psicologia")
                        // Endpoints Sucesos
                        .requestMatchers("/api/suceso/**").hasAnyAuthority("ROLE_Psicologia", "ROLE_Bienestar")
                        // Endpoints Diagnostico
                        .requestMatchers("/api/diagnostico/**").hasAnyAuthority("ROLE_Psicologia")
                        // Endpoints AtencionAlumno
                        .requestMatchers("/api/atencionalumno/**").hasAnyAuthority("ROLE_Psicologia","ROLE_Practicante")
                        // Endpoints AtencionApoderado
                        .requestMatchers("/api/atencionapoderado/**").hasAnyAuthority("ROLE_Psicologia","ROLE_Practicante")
                        // Endpoints Asignar
                        .requestMatchers("/api/asignar/**").hasAnyAuthority("ROLE_Psicologia","ROLE_Practicante")


                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
