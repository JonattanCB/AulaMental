package com.abs.aulamental.mapper;

import com.abs.aulamental.model.Persona;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PersonaMapper {

    public static String toConcatNombre(Persona persona){
        return Stream.of(persona.getNombre(), persona.getApaterno(), persona.getAmaterno())
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(s -> !s.trim().isEmpty())
                .collect(Collectors.joining(" "));
    }

    private PersonaMapper() {}

}
