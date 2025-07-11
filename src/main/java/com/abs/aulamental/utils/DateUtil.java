package com.abs.aulamental.utils;

import com.abs.aulamental.exception.ValidarExcepciones;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

@Component
public class DateUtil {
    public static Timestamp nowTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
    public static DayOfWeek getTodayDayOfWeek() {
        return LocalDate.now().getDayOfWeek();
    }
    public static Date getTodaySqlDate() {
        return Date.valueOf(LocalDate.now());
    }

    public static int calculateAge(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new ValidarExcepciones("La fecha de nacimiento no puede ser nula");
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public static String obtenerNombreMes(int mes) {
        return switch (mes) {
            case 1 -> "Enero";
            case 2 -> "Febrero";
            case 3 -> "Marzo";
            case 4 -> "Abril";
            case 5 -> "Mayo";
            case 6 -> "Junio";
            case 7 -> "Julio";
            case 8 -> "Agosto";
            case 9 -> "Septiembre";
            case 10 -> "Octubre";
            case 11 -> "Noviembre";
            case 12 -> "Diciembre";
            default -> "Mes inválido";
        };
    }

}
