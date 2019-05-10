package com.hiring.helder.services;

import com.hiring.helder.Repositories.CashBackRepository;
import com.hiring.helder.exceptions.CashBackException;
import com.hiring.helder.models.CashBack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.hiring.helder.constants.StringConstants.DUMMY;
import static com.hiring.helder.enums.GenerosEnum.CLASSIC;
import static com.hiring.helder.utils.BuildEntity.getCashBack;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CashBackServiceTest {

    @InjectMocks
    private CashBackService cashBackService;

    @Mock
    private CashBackRepository cashBackRepository;

    @Test
    public void findByIdWithSucess() throws CashBackException {

        CashBack cashBack = getCashBack();

        when(cashBackRepository.findById(CLASSIC.getNome())).thenReturn(Optional.of(cashBack));
        CashBack retorno = cashBackService.findById(CLASSIC.getNome());

        Assert.assertNotNull(retorno);
        Assert.assertEquals(retorno.getId(), cashBack.getId());
        Assert.assertEquals(CLASSIC.getNome(), retorno.getId());

        verify(cashBackRepository, times(1)).findById(CLASSIC.getNome());
    }

    @Test(expected = CashBackException.class)
    public void findByIdWithFailure() throws CashBackException {
        when(cashBackRepository.findById(DUMMY)).thenThrow(IllegalArgumentException.class);
        cashBackService.findById(CLASSIC.getNome());
        verify(cashBackService, times(1)).findById(CLASSIC.getNome());
    }
}
