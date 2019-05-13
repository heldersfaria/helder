package com.hiring.helder.controllers;

import com.hiring.helder.exceptions.DiscoVinilNaoEncontradoException;
import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.services.DiscoVinilService;
import com.hiring.helder.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hiring.helder.enums.GenerosEnum.POP;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@WebMvcTest(DiscosController.class)
public class DiscosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscoVinilService discoVinilService;

    public static final String NAOEXISTE = "naoexiste";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String GENERO = "genero";



    private static final String URL_ID = "/discos/%s";

    private static final String URL = "/discos";

    private String payload;

    private String listPayload;

    private DiscoVinil discoPop, discoPop2, discoRock;

    private List<DiscoVinil> discos;

    @Before
    public void before() {
        TestUtils utils = new TestUtils();

        payload = utils.readFromResources("popDiscoEntity.json");

        discoPop = utils.readFromResources(DiscoVinil.class, "popDiscoEntity.json");
        discoPop2 = utils.readFromResources(DiscoVinil.class, "pop2DiscoEntity.json");
        discoRock = utils.readFromResources(DiscoVinil.class, "rockDiscoEntity.json");
    }

    @Test
    public void findByIdComSucesso() throws Exception {
        when(discoVinilService.findById(discoPop.getId())).thenReturn(discoPop);
        mockMvc.perform(get(String.format(URL_ID, discoPop.getId()))).andExpect(status().isOk()).andExpect(content().json(payload));
    }

    @Test
    public void findByIdWithoutSuccess() throws Exception {
        when(discoVinilService.findById(NAOEXISTE)).thenThrow(new DiscoVinilNaoEncontradoException());
        mockMvc.perform(get(String.format(URL_ID, NAOEXISTE))).andExpect(status().isNotFound());
    }

    @Test
    public void findWithGenero() throws Exception {
        discos = Arrays.asList(discoPop, discoPop2);
        listPayload = new TestUtils().readFromResources("listPopDiscos.json");

        when(discoVinilService.find(POP.getNome(), 50, 1)).thenReturn(discos);
        mockMvc.perform(get(String.format(URL))
                .param(GENERO, POP.getNome())
                .param(PAGE, "1")
                .param(SIZE, "50")).andExpect(status().isOk()).andExpect(content().json(listPayload));

    }

    @Test
    public void findWithoutGenero() throws Exception {
        listPayload = new TestUtils().readFromResources("listVariosGeneros.json");
        discos = new ArrayList<>(Arrays.asList(discoPop, discoPop2, discoRock));

        when(discoVinilService.find(null, 50, 1)).thenReturn(discos);
        mockMvc.perform(get(String.format(URL))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isOk()).andExpect(content().json(listPayload));
    }
}