package com.hiring.helder.utils;

import com.hiring.helder.models.CashBack;
import com.hiring.helder.models.DiscoCashBack;
import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.models.Venda;

import java.util.Arrays;
import java.util.Random;

import static com.hiring.helder.constants.StringConstants.ID;
import static com.hiring.helder.constants.StringConstants.NOME;
import static com.hiring.helder.enums.GenerosEnum.CLASSIC;

public final class BuildEntity {

    public static Double valorTotalCashBack = 10.0;


    private BuildEntity() {

    }

    public static CashBack getCashBack() {
        CashBack cashBack = new CashBack();
        cashBack.setId(CLASSIC.getNome());
        cashBack.setDescontos(Arrays.asList(1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1));
        return cashBack;
    }

    public static DiscoCashBack getDiscoCashBack() {
        DiscoCashBack discoCashBack = new DiscoCashBack();

        discoCashBack.setPreco(new Random().nextDouble());
        discoCashBack.setId(ID);
        discoCashBack.setValorComCashBack(new Random().nextDouble());
        discoCashBack.setGenero(CLASSIC.getNome());
        discoCashBack.setNome(NOME);

        return discoCashBack;
    }

    public static DiscoVinil getDiscoVinil() {
        DiscoVinil discoVinil = new DiscoVinil();

        discoVinil.setPreco(new Random().nextDouble());
        discoVinil.setId(ID);
        discoVinil.setGenero(CLASSIC.getNome());
        discoVinil.setNome(NOME);

        return discoVinil;
    }

    public static Venda getVenda() {
        Venda venda = new Venda();

        venda.setId(ID);
        venda.setValorTotalCashBack(valorTotalCashBack);
        venda.setDiscos(Arrays.asList(getDiscoCashBack()));

        return venda;
    }
}
