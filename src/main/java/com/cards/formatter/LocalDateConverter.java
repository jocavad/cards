package com.cards.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.format.Formatter;


public class LocalDateConverter implements Formatter<LocalDate>{

private final DateTimeFormatter dtf;
    
    public LocalDateConverter(String format) {
        dtf=DateTimeFormatter.ofPattern(format);
    }
    
    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(dtf);
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, dtf);
    }
}