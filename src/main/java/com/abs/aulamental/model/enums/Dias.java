package com.abs.aulamental.model.enums;

import com.abs.aulamental.exception.ValidarExcepciones;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Dias {
    DOMINGO(0, "SUNDAY"),
    LUNES(1, "MONDAY"),
    MARTES(2, "TUESDAY"),
    MIERCOLES(3, "WEDNESDAY"),
    JUEVES(4, "THURSDAY"),
    VIERNES(5, "FRIDAY"),
    SABADO(6, "SATURDAY");

    private final int dia;
    private final String englishName;

    Dias(int dia, String englishName) {
        this.dia = dia;
        this.englishName = englishName;
    }

    public int getDia() {
        return dia;
    }

    public String getEnglishName() {
        return englishName;
    }

    @JsonCreator
    public static Dias fromString(String value) {
        for (Dias dia : Dias.values()) {
            if (dia.name().equalsIgnoreCase(value) || dia.toString().equalsIgnoreCase(value)) {
                return dia;
            }
        }
        throw new IllegalArgumentException("Día inválido: " + value);
    }


    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }

    public static Dias fromDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return LUNES;
            case TUESDAY: return MARTES;
            case WEDNESDAY: return MIERCOLES;
            case THURSDAY: return JUEVES;
            case FRIDAY: return VIERNES;
            case SATURDAY: return SABADO;
            case SUNDAY: return DOMINGO;
            default: throw new ValidarExcepciones("Dia valido: "+dayOfWeek);
        }
    }
}

