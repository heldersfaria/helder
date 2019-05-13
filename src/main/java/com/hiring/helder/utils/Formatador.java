package com.hiring.helder.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.hiring.helder.constants.StringConstants.DATE_FORMAT;
import static java.math.RoundingMode.DOWN;

public final class Formatador {

    private Formatador() {

    }

    public static Double formatarDouble(Double valor) {
        return new BigDecimal(valor).setScale(2, DOWN).doubleValue();
    }


    public static LocalDate converterStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static JsonElement converterLocalDateTtoJsonElement(LocalDate date) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }


    public static String converterLocalDateTtoString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
