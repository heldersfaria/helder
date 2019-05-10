package com.hiring.helder.services;

import com.hiring.helder.Repositories.DiscoCashBackRepository;
import com.hiring.helder.models.DiscoCashBack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.hiring.helder.constants.StringConstants.ID;
import static com.hiring.helder.utils.BuildEntity.getDiscoCashBack;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DiscoCashBackServiceTest {

    @InjectMocks
    private DiscoCashBackService discoCashBackService;

    @Mock
    private DiscoCashBackRepository discoCashBackRepository;

    @Test
    public void saveDiscoCashBackComSucesso() {

        DiscoCashBack discoCashBack = getDiscoCashBack();

        when(discoCashBackRepository.save(discoCashBack)).thenReturn(discoCashBack);

        DiscoCashBack retorno = discoCashBackService.save(discoCashBack);

        Assert.assertNotNull(retorno);
        Assert.assertEquals(retorno.getId(), ID);

        verify(discoCashBackRepository, times(1)).save(discoCashBack);
    }
}
