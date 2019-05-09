package com.hiring.helder.services;

import com.hiring.helder.Repositories.CashBackRepository;
import com.hiring.helder.constants.StringConstants;
import com.hiring.helder.enums.GenerosEnum;
import com.hiring.helder.models.CashBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CashBackService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CashBackRepository cashBackRepository;

    @Value("#{'${cashBack.pop}'.split(',')}")
    private List<Double> descontosPop;

    @Value("#{'${cashBack.mpb}'.split(',')}")
    private List<Double> descontosMpb;

    @Value("#{'${cashBack.classic}'.split(',')}")
    private List<Double> descontosClassic;

    @Value("#{'${cashBack.rock}'.split(',')}")
    private List<Double> descontosRock;

    private void buildCashBack(String genero) {

        CashBack cashBack = new CashBack();
        cashBack.setId(genero);

        HashMap<String, Double> descontos = new HashMap<>();

        switch (genero) {
            case StringConstants.ROCK:
                cashBack.setDescontos(descontosRock);
                break;
            case StringConstants.MPB:
                cashBack.setDescontos(descontosMpb);
                break;
            case StringConstants.POP:
                cashBack.setDescontos(descontosPop);
                break;
            case StringConstants.CLASSIC:
                cashBack.setDescontos(descontosClassic);
                break;

            default:
        }

        cashBackRepository.save(cashBack);
    }

    public CashBack findById(String id) {
        return cashBackRepository.findById(id).get();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        cashBackRepository.deleteAll();

        for (GenerosEnum generosEnum : GenerosEnum.values()) {
            buildCashBack(generosEnum.getNome());
        }
    }
}
