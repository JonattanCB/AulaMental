package com.abs.aulamental.mapper;

import com.abs.aulamental.external.reniec.dto.RenicDto;
import com.abs.aulamental.model.Persona;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PersonaMapper {

    public static RenicDto toFormatReniec(RenicDto dto){
        return new RenicDto(toTitleCase(dto.nombres()),toTitleCase(dto.apellidoPaterno()), toTitleCase(dto.apellidoMaterno()),dto.tipoDocumento(), dto.numeroDocumento(),dto.digitoVerificador());
    }

    public static String toConcatNombre(Persona persona){
        return Stream.of(persona.getNombre(), persona.getApaterno(), persona.getAmaterno())
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(s -> !s.trim().isEmpty())
                .collect(Collectors.joining(" "));
    }

    public static String obtenerIniciales(Persona persona) {
        String[] palabras = toConcatNombre(persona).trim().split("\\s+");
        if (palabras.length >= 2) {
            return ("" + palabras[0].charAt(0) + palabras[1].charAt(0)).toUpperCase();
        } else if (palabras.length == 1) {
            return ("" + palabras[0].charAt(0)).toUpperCase();
        }
        return "US";
    }

    public static String toTitleCase(String cadena) {
        if (cadena == null || cadena.isBlank()) {
            return cadena;
        }
        return Arrays.stream(cadena.trim().toLowerCase().split("\\s+"))
                .map(palabra -> Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1))
                .collect(Collectors.joining(" "));
    }


    private PersonaMapper() {}

}
