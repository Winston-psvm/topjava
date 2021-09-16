package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class DateTimeFormatter {
    public static class DateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalDate(text);
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class TimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalTime(text);
        }

        @Override
        public String print(LocalTime object, Locale locale) {
            return object.format(java.time.format.DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
