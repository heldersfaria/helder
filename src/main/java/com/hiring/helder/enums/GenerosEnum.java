package com.hiring.helder.enums;

import com.hiring.helder.constants.StringConstants;

public enum GenerosEnum {

    POP(StringConstants.POP), MPB(StringConstants.MPB), ROCK(StringConstants.ROCK), CLASSIC(StringConstants.CLASSIC);

    private String nome;

    private GenerosEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
