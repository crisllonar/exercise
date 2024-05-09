package org.example.helper.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    /**
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDateFormatted(String pattern) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return currentDate.format(formatter);
    }
}
