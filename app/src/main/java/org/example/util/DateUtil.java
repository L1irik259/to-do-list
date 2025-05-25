package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

public class DateUtil {

    private static final List<String> DATE_FORMATS = Arrays.asList(
        "yyyy-MM-dd",
        "dd/MM/yyyy",
        "dd.MM.yyyy",
        "MM/dd/yyyy",
        "yyyy-MM-dd HH:mm:ss",
        "dd-MM-yyyy HH:mm",
        "yyyyMMdd"
    );

    public static Date parseStringToDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new IllegalArgumentException("Дата не может быть пустой");
        }

        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false); // Строгая проверка формата
                return sdf.parse(dateString.trim());
            } catch (ParseException e) {
                // Пробуем следующий формат
            }
        }

        throw new IllegalArgumentException(
            String.format("Не удалось распознать дату '%s'. Поддерживаемые форматы: %s", 
            dateString, String.join(", ", DATE_FORMATS))
        );
    }
    public static Date parseStringToDate(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            return sdf.parse(dateString.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                String.format("Дата '%s' не соответствует формату '%s'", dateString, format), e);
        }
    }
}