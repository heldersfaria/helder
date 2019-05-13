package com.hiring.helder.controllers;

import com.hiring.helder.Resources.VendaResource;
import com.hiring.helder.exceptions.VendaNaoEncontradaException;
import com.hiring.helder.exceptions.VendaSemDiscoException;
import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.models.Venda;
import com.hiring.helder.services.VendaService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.hiring.helder.constants.StringConstants.*;
import static com.hiring.helder.utils.Formatador.converterLocalDateTtoString;
import static java.time.LocalDate.now;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@WebMvcTest(VendasController.class)
public class VendasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendaService vendaService;

    private static final String URL_ID = "/vendas/%s";
    private static final String URL = "/vendas";

    private String payload;

    private Venda venda;

    private TestUtils utils = new TestUtils();

    private DiscoVinil discoVendido, discoPop;

    private LocalDate hoje;

    @Before
    public void before() {

        discoPop = utils.readFromResources(DiscoVinil.class, "popDiscoEntity.json");
        discoVendido = utils.readFromResources(DiscoVinil.class, "discoVendido.json");

        payload = utils.readFromResources("vendaEntity.json");

        venda = utils.readFromResourcesFromPayload(Venda.class, payload);

        hoje = LocalDate.now();
    }

    @Test
    public void findByIdComSucesso() throws Exception {
        when(vendaService.findById(venda.getId())).thenReturn(venda);
        mockMvc.perform(get(String.format(URL_ID, venda.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(payload));
    }

    @Test
    public void findByIdWithoutSuccess() throws Exception {
        when(vendaService.findById(NAOEXISTE)).thenThrow(new VendaNaoEncontradaException());
        mockMvc.perform(get(String.format(URL_ID, NAOEXISTE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void criarVendaComSucesso() throws Exception {

        venda.setData(LocalDate.now());
        VendaResource vendaResource = new VendaResource();
        vendaResource.setDiscos(Arrays.asList(discoVendido));
        payload = utils.readFromEntity(vendaResource);

        when(vendaService.processarVenda(vendaResource)).thenReturn(venda);

        mockMvc.perform(post(URL_ID).contentType(APPLICATION_JSON).content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorTotalCashBack").isNotEmpty())
                .andExpect(jsonPath("$.data").value(converterLocalDateTtoString(now())));
    }

    @Test
    public void tentarCriarVendaSemDisco() throws Exception {

        VendaResource resource = new VendaResource();

        payload = utils.readFromEntity(resource);

        when(vendaService.processarVenda(resource)).thenThrow(VendaSemDiscoException.class);

        mockMvc.perform(post(URL_ID).contentType(APPLICATION_JSON).content(payload)).andExpect(status().isBadRequest());
    }

    @Test
    public void tentarCriarVendaComDiscoInvalido() throws Exception {

        VendaResource resource = new VendaResource();
        discoPop.setId(NAOEXISTE);
        discoPop.setNome(NAOEXISTE);
        resource.setDiscos(Arrays.asList(discoPop));

        payload = utils.readFromEntity(resource);

        when(vendaService.processarVenda(resource)).thenThrow(VendaSemDiscoException.class);

        mockMvc.perform(post(URL_ID).contentType(APPLICATION_JSON).content(payload)).andExpect(status().isBadRequest());
    }

    @Test
    public void find() throws Exception {
        LocalDate ontem = hoje.minusDays(1);
        LocalDate amanha = hoje.plusMonths(1);

        venda.setData(hoje);

        List<Venda> vendas = Arrays.asList(venda);

        String listPayload = utils.readFromEntity(vendas);

        when(vendaService.find(ontem, amanha, 1, 50)).thenReturn(vendas);
        mockMvc.perform(get(String.format(URL))
                .param(DATA_INICIAL, converterLocalDateTtoString(ontem))
                .param(DATA_FINAL, converterLocalDateTtoString(amanha))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isOk())
                .andExpect(content().json(listPayload));
    }

    @Test
    public void findInternalServerErrorDataInicio() throws Exception {
        LocalDate ontem = hoje.minusDays(1);

        mockMvc.perform(get(String.format(URL))
                .param(DATA_INICIAL, converterLocalDateTtoString(ontem))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findInternalServerErrorDataFinal() throws Exception {
        LocalDate amanha = hoje.plusMonths(1);

        mockMvc.perform(get(String.format(URL))
                .param(DATA_FINAL, converterLocalDateTtoString(amanha))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isInternalServerError());
    }
}