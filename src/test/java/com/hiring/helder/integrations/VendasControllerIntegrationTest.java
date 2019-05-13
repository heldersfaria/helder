package com.hiring.helder.integrations;

import com.hiring.helder.Resources.VendaResource;
import com.hiring.helder.exceptions.CashBackException;
import com.hiring.helder.exceptions.VendaComDiscoVinilInvalidoException;
import com.hiring.helder.exceptions.VendaSemDiscoException;
import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.models.Venda;
import com.hiring.helder.services.VendaService;
import com.hiring.helder.utils.Formatador;
import com.hiring.helder.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.hiring.helder.constants.StringConstants.*;
import static java.time.LocalDate.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.properties")
public class VendasControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendaService vendaService;


    private static final String URL_ID = "/vendas/%s";
    private static final String URL = "/vendas";

    private String payload;

    private DiscoVinil discoPop, discoPop2, discoRock;

    private TestUtils utils = new TestUtils();

    private Venda venda;

    private LocalDate hoje;

    @Before
    public void before() throws CashBackException, VendaSemDiscoException, VendaComDiscoVinilInvalidoException {

        discoPop = utils.readFromResources(DiscoVinil.class, "popDiscoEntity.json");
        discoPop2 = utils.readFromResources(DiscoVinil.class, "pop2DiscoEntity.json");
        discoRock = utils.readFromResources(DiscoVinil.class, "rockDiscoEntity.json");

        VendaResource resource = new VendaResource();
        resource.setDiscos(Arrays.asList(discoPop, discoPop2, discoRock));

        venda = vendaService.processarVenda(resource);

        hoje = LocalDate.now();
    }

    @Test
    public void findByIdComSucesso() throws Exception {
        mockMvc.perform(get(String.format(URL_ID, venda.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.id").value(venda.getId()));
    }

    @Test
    public void findByIdWithoutSuccess() throws Exception {
        mockMvc.perform(get(String.format(URL_ID, NAOEXISTE))).andExpect(status().isNotFound());
    }

    @Test
    public void criarVendaComSucesso() throws Exception {

        VendaResource resource = new VendaResource();
        resource.setDiscos(Arrays.asList(discoPop, discoPop2, discoRock));

        venda = vendaService.processarVenda(resource);

        payload = utils.readFromEntity(resource);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.valorTotalCashBack").isNotEmpty())
                .andExpect(jsonPath("$.data").value(Formatador.converterLocalDateTtoString(now())));
    }

    @Test
    public void tentarCriarVendaSemDisco() throws Exception {

        VendaResource resource = new VendaResource();

        payload = utils.readFromEntity(resource);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void tentarCriarVendaComDiscoInvalido() throws Exception {

        VendaResource resource = new VendaResource();
        discoPop.setId(NAOEXISTE);
        discoPop.setNome(NAOEXISTE);
        resource.setDiscos(Arrays.asList(discoPop));

        payload = utils.readFromEntity(resource);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void find() throws Exception {
        LocalDate ontem = hoje.minusDays(1);
        LocalDate amanha = hoje.plusMonths(1);

        mockMvc.perform(get(String.format(URL))
                .param(DATA_INICIAL, Formatador.converterLocalDateTtoString(ontem))
                .param(DATA_FINAL, Formatador.converterLocalDateTtoString(amanha))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8));
    }

    @Test
    public void findInternalServerErrorDataInicio() throws Exception {
        LocalDate ontem = hoje.minusDays(1);

        mockMvc.perform(get(String.format(URL))
                .param(DATA_INICIAL, Formatador.converterLocalDateTtoString(ontem))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findInternalServerErrorDataFinal() throws Exception {
        LocalDate amanha = hoje.plusMonths(1);

        mockMvc.perform(get(String.format(URL))
                .param(DATA_FINAL, Formatador.converterLocalDateTtoString(amanha))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isInternalServerError());
    }
}
