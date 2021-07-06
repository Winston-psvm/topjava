package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, @Nullable T start,@Nullable T end){
        return (start == null || lt.compareTo(start) >= 0) && (end == null || lt.compareTo(end) < 0);
    }

    public static LocalDate refactorStartDate(@Nullable LocalDate startDate){
        return startDate == null ? LocalDate.MIN : LocalDate.from(startDate.atStartOfDay());
    }

    public static LocalDate refactorEndDate(@Nullable LocalDate endDate){
        return endDate == null ? LocalDate.MAX : LocalDate.from(endDate.atStartOfDay().plus(1, ChronoUnit.DAYS));
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

