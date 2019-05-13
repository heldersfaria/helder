package com.hiring.helder.integrations;

import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.hiring.helder.constants.StringConstants.NAOEXISTE;
import static com.hiring.helder.constants.StringConstants.PAGE;
import static com.hiring.helder.controllers.DiscosControllerTest.GENERO;
import static com.hiring.helder.controllers.DiscosControllerTest.SIZE;
import static com.hiring.helder.enums.GenerosEnum.POP;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.properties")
public class DiscosControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_ID = "/discos/%s";

    private static final String URL = "/discos";

    private DiscoVinil discoPop;

    @Before
    public void before() {
        discoPop = new TestUtils().readFromResources(DiscoVinil.class, "popDiscoEntity.json");
    }

    @Test
    public void findByIdComSucesso() throws Exception {
        mockMvc.perform(get(String.format(URL_ID, discoPop.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(discoPop.getId()));
        ;
    }

    @Test
    public void findByIdWithoutSuccess() throws Exception {
        mockMvc.perform(get(String.format(URL_ID, NAOEXISTE))).andExpect(status().isNotFound());
    }

    @Test
    public void findWithGenero() throws Exception {

        mockMvc.perform(get(String.format(URL))
                .param(GENERO, POP.getNome())
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("[*]").isNotEmpty());
    }

    @Test
    public void findWithoutGenero() throws Exception {

        mockMvc.perform(get(String.format(URL))
                .param(PAGE, "1")
                .param(SIZE, "50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("[*]").isNotEmpty());
    }
}