package com.abs.aulamental.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

}
