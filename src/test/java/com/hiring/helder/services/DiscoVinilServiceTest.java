package com.hiring.helder.services;


import com.hiring.helder.Repositories.DiscoVinilRepository;
import com.hiring.helder.exceptions.DiscoVinilNaoEncontradoException;
import com.hiring.helder.models.DiscoVinil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.hiring.helder.constants.StringConstants.*;
import static com.hiring.helder.enums.GenerosEnum.CLASSIC;
import static com.hiring.helder.utils.BuildEntity.getDiscoVinil;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RunWith(SpringRunner.class)
public class DiscoVinilServiceTest {

    @InjectMocks
    private DiscoVinilService discoVinilService;

    @Mock
    private DiscoVinilRepository discoVinilRepository;

    private static Integer page = 1;
    private static Integer size = 50;
    private PageRequest pageRequest;
    private DiscoVinil discoVinil;
    private Page<DiscoVinil> pagedResponse;

    @Before
    public void before() {
        this.discoVinil = getDiscoVinil();

        List<DiscoVinil> discos = Arrays.asList(discoVinil);
        pagedResponse = new PageImpl(discos);
        pageRequest = PageRequest.of(page, size, Sort.by(ASC, NOME));
    }

    @Test
    public void findByIdWithSucess() throws DiscoVinilNaoEncontradoException {

        when(discoVinilRepository.findById(ID)).thenReturn(Optional.of(discoVinil));

        DiscoVinil retorno = discoVinilService.findById(ID);

        assertNotNull(retorno);
        assertEquals(retorno.getId(), discoVinil.getId());

        verify(discoVinilRepository, times(1)).findById(ID);
    }

    @Test(expected = DiscoVinilNaoEncontradoException.class)
    public void findByIdWithFailure() throws DiscoVinilNaoEncontradoException {
        when(discoVinilRepository.findById(DUMMY)).thenThrow(IllegalArgumentException.class);

        discoVinilService.findById(ID);

        verify(discoVinilService, times(1)).findById(ID);
    }

    @Test
    public void saveDiscoVinilComSucesso() {
        when(discoVinilRepository.save(discoVinil)).thenReturn(discoVinil);

        DiscoVinil retorno = discoVinilService.save(ID, CLASSIC.getNome(), NOME);

        assertNotNull(retorno);
        assertEquals(retorno.getId(), ID);

        verify(discoVinilRepository, times(1)).save(discoVinil);
    }

    @Test
    public void hasDataTrue() {
        when(discoVinilRepository.count()).thenReturn(1L);

        Assert.assertTrue(discoVinilService.hasData());

        verify(discoVinilRepository, times(1)).count();
    }

    @Test
    public void hasDataFalse() {
        when(discoVinilRepository.count()).thenReturn(0l);

        assertFalse(discoVinilService.hasData());

        verify(discoVinilRepository, times(1)).count();
    }

    @Test
    public void findManyByGenero() {
        when(discoVinilRepository.findByGenero(CLASSIC.getNome(), pageRequest)).thenReturn(pagedResponse);

        List<DiscoVinil> discosRetorno = discoVinilService.find(CLASSIC.getNome(), size, page);

        assertNotNull(discosRetorno);
        discosRetorno.forEach(disco -> assertTrue(disco.getGenero().equals(CLASSIC.getNome())));

        verify(discoVinilRepository, times(1)).findByGenero(CLASSIC.getNome(), pageRequest);
    }


    @Test
    public void findMany() {
        when(discoVinilRepository.findAll(pageRequest)).thenReturn(pagedResponse);

        List<DiscoVinil> discosRetorno = discoVinilService.find(null, size, page);

        assertNotNull(discosRetorno);
        assertTrue(discosRetorno.size() > 0);

        verify(discoVinilRepository, times(1)).findAll(pageRequest);
    }
}
