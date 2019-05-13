package com.hiring.helder.services;

import com.hiring.helder.Repositories.DiscoVinilRepository;
import com.hiring.helder.exceptions.DiscoVinilNaoEncontradoException;
import com.hiring.helder.models.DiscoVinil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

import static com.hiring.helder.constants.StringConstants.NOME;
import static com.hiring.helder.utils.Formatador.formatarDouble;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class DiscoVinilService {

    @Autowired
    private DiscoVinilRepository discoVinilRepository;

    public List<DiscoVinil> find(String genero, Integer size, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(ASC, NOME));

        if (!StringUtils.isEmpty(genero)) {
            return discoVinilRepository.findByGenero(genero, pageRequest).getContent();
        }

        return discoVinilRepository.findAll(pageRequest).getContent();
    }

    public DiscoVinil findById(String id) throws DiscoVinilNaoEncontradoException {

        try {
            return discoVinilRepository.findById(id).get();
        } catch (Exception e) {
            throw new DiscoVinilNaoEncontradoException("Disco não encontrado.", e);
        }
    }

    public DiscoVinil save(String id, String genero, String nome) {

        DiscoVinil discoVinil = new DiscoVinil();
        discoVinil.setId(id);
        discoVinil.setPreco(formatarDouble(new Random().doubles(0.00, 100.00).findAny().getAsDouble()));
        discoVinil.setNome(nome);
        discoVinil.setGenero(genero);

        return discoVinilRepository.save(discoVinil);
    }

    public boolean hasData() {
        return discoVinilRepository.count() > 0;
    }
}