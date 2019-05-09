package com.hiring.helder.utils;

import java.math.BigDecimal;

import static java.math.RoundingMode.DOWN;

public final class Formatador {

    private Formatador() {

    }

    public static Double formatarDouble(Double valor) {
        return new BigDecimal(valor).setScale(2, DOWN).doubleValue();
    }
}
