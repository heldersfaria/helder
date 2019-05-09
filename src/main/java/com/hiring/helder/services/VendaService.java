package com.hiring.helder.services;

import com.hiring.helder.Repositories.VendaRepository;
import com.hiring.helder.Resources.VendaResource;
import com.hiring.helder.exceptions.VendaException;
import com.hiring.helder.models.DiscoCashBack;
import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.models.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hiring.helder.constants.StringConstants.DATA;
import static com.hiring.helder.utils.Formatador.formatarDouble;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private DiscoVinilService discoVinilService;

    @Autowired
    private CashBackService cashBackService;

    @Autowired
    private DiscoCashBackService discoCashBackService;

    public Venda findById(String id) {
        return vendaRepository.findById(id).get();
    }

    public List<Venda> find(LocalDate dateInicial, LocalDate dateFinal, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(DESC, DATA));
        return vendaRepository.findByDataBetween(dateInicial, dateFinal, pageRequest);
    }

    public Venda processarVenda(VendaResource vendaResource) throws VendaException {

        List<DiscoVinil> discos = vendaResource.getDiscos();

        if (discos != null && discos.size() > 0) {

            Double valorTotalCashBack = 0.0;

            List<DiscoCashBack> listaDiscosWithCashBack = new ArrayList<DiscoCashBack>();

            int dia = now().getDayOfWeek().getValue();

            for (DiscoVinil disco : discos) {

                DiscoVinil discoFromDatabase = discoVinilService.findById(disco.getId());

                Double descontoDoDia = cashBackService.findById(disco.getGenero()).getDescontos().get(dia);

                Double valorDiscoCashBack = discoFromDatabase.getPreco() * (descontoDoDia / 100);

                valorTotalCashBack += valorDiscoCashBack;

                String idCashBack = randomUUID().toString();

                DiscoCashBack discoCashBack = new DiscoCashBack();

                discoCashBack.setId(idCashBack);
                discoCashBack.setGenero(discoFromDatabase.getGenero());
                discoCashBack.setNome(discoFromDatabase.getNome());
                discoCashBack.setValorComCashBack(valorDiscoCashBack);
                discoCashBack.setPreco(discoFromDatabase.getPreco());

                listaDiscosWithCashBack.add(discoCashBackService.save(discoCashBack));
            }

            return salvarVenda(formatarDouble(valorTotalCashBack), listaDiscosWithCashBack);
        }

        throw new VendaException();
    }

    public Venda salvarVenda(Double valorTotalCashBack, List<DiscoCashBack> listaDiscosWithCashBack) {
        Venda venda = new Venda();
        venda.setId(randomUUID().toString());
        venda.setData(now());
        venda.setDiscos(listaDiscosWithCashBack);
        venda.setValorTotalCashBack(valorTotalCashBack);
        return vendaRepository.save(venda);
    }
}