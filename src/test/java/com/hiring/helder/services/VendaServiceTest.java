package com.hiring.helder.services;


import com.hiring.helder.Repositories.VendaRepository;
import com.hiring.helder.exceptions.VendaException;
import com.hiring.helder.models.Venda;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.hiring.helder.constants.StringConstants.*;
import static com.hiring.helder.utils.BuildEntity.getVenda;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RunWith(SpringRunner.class)
public class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    private Venda venda;

    private static Integer page = 1;
    private static Integer size = 50;
    private PageRequest pageRequest;

    private LocalDate hoje;

    @Before
    public void before() {
        venda = getVenda();
        pageRequest = PageRequest.of(page, size, Sort.by(DESC, DATA));
        hoje = LocalDate.now();
    }

    @Test
    public void findByIdWithSucess() throws VendaException {
        when(vendaRepository.findById(ID)).thenReturn(Optional.of(venda));
        Venda retorno = vendaService.findById(ID);

        Assert.assertNotNull(retorno);
        Assert.assertEquals(retorno.getId(), ID);

        verify(vendaRepository, times(1)).findById(ID);
    }

    @Test(expected = VendaException.class)
    public void findByIdWithFailure() throws VendaException {
        when(vendaRepository.findById(DUMMY)).thenThrow(IllegalArgumentException.class);

        vendaService.findById(ID);

        verify(vendaService, times(1)).findById(ID);
    }

    @Test
    public void findAllWithDifferentDays() {

        LocalDate ontem = hoje.minusDays(1);
        LocalDate amanha = hoje.plusMonths(1);

        venda.setData(hoje);

        Venda venda2 =  getVenda();
        venda2.setData(hoje.plusDays(3));


        List<Venda> discos = Arrays.asList(venda, venda2);

        when(vendaRepository.findByDataBetween(ontem, amanha, pageRequest)).thenReturn(discos);

        List<Venda> vendasRetorno = vendaService.find(ontem, amanha, page, size);

        assertNotNull(vendasRetorno);

        vendasRetorno.forEach(

                item -> assertTrue((ontem.isBefore(venda.getData()) || ontem.isEqual(venda.getData())) &&
                        (amanha.isAfter(venda.getData()) || amanha.isEqual(venda.getData()))));

        assertTrue(vendasRetorno.size() == 2);

        verify(vendaRepository, times(1)).findByDataBetween(ontem, amanha, pageRequest);
    }


    @Test
    public void findAllThreeEqualsDays() {
        LocalDate ontem = hoje;
        LocalDate amanha = hoje;

        venda.setData(hoje);

        Venda venda2 = getVenda();
        venda2.setData(hoje);

        List<Venda> discos = Arrays.asList(venda, venda2);

        when(vendaRepository.findByDataBetween(ontem, amanha, pageRequest)).thenReturn(discos);

        List<Venda> vendasRetorno = vendaService.find(ontem, amanha, page, size);

        assertNotNull(vendasRetorno);

        vendasRetorno.forEach(
                item -> assertTrue(ontem.isEqual(amanha) && ontem.isEqual(venda.getData()) && amanha.isEqual(venda.getData())));

        verify(vendaRepository, times(1)).findByDataBetween(ontem, amanha, pageRequest);
    }
}