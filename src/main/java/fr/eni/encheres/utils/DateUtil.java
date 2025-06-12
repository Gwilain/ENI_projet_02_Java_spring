package fr.eni.encheres.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("dateUtil")
public class DateUtil {

    public String format(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));

    }
}
