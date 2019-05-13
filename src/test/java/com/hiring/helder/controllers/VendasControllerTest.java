//package com.hiring.helder.controllers;
//
//import com.hiring.helder.exceptions.VendaNaoEncontradaException;
//import com.hiring.helder.models.Venda;
//import com.hiring.helder.services.CashBackService;
//import com.hiring.helder.services.DiscoCashBackService;
//import com.hiring.helder.services.DiscoVinilService;
//import com.hiring.helder.services.VendaService;
//import com.hiring.helder.utils.TestUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ActiveProfiles("test")
//@TestPropertySource("classpath:application.properties")
//@RunWith(SpringRunner.class)
//@WebMvcTest(VendasController.class)
//public class VendasControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private VendaService vendaService;
//
//    @MockBean
//    private CashBackService cashBackService;
//
//    @MockBean
//    private DiscoVinilService discoVinilService;
//
//    @MockBean
//    private DiscoCashBackService discoCashBackService;
//
//    private static final String URL = "/vendas/%s";
//    private String payload;
//
//    private Venda venda;
//
//    @Before
//    public void before() {
//        TestUtils utils = new TestUtils();
//        payload = utils.readFromResources("vendaEntity.json");
//        System.out.println(payload);
//        venda = utils.readFromResourcesFromPayload(Venda.class, payload);
//    }
//
//    @Test
//    public void findByIdComSucesso() throws Exception {
//        when(vendaService.findById(venda.getId())).thenReturn(venda);
//        mockMvc.perform(get(String.format(URL, venda.getId()))).andExpect(status().isOk()).andExpect(content().json(payload));
//    }
//
//    @Test
//    public void findByIdWithoutSuccess() throws Exception {
//        when(vendaService.findById("naoexiste")).thenThrow(new VendaNaoEncontradaException());
//        mockMvc.perform(get(String.format(URL, "naoexiste"))).andExpect(status().isNotFound());
//    }
//
//
////    @GetMapping("/vendas")
////    public ResponseEntity<List<Venda>> find(@RequestParam("dataInicial")
////                                            @DateTimeFormat(pattern = "dd-MM-yyyy")
////                                                    LocalDate dataInicial,
////                                            @RequestParam("dataFinal")
////                                            @DateTimeFormat(pattern = "dd-MM-yyyy")
////                                                    LocalDate dataFinal,
////                                            @RequestParam(value = "page", required = false, defaultValue = "0") String page,
////                                            @RequestParam(value = "size", required = false, defaultValue = "50") String size) {
////        List<Venda> lista = vendaService.find(dataInicial, dataFinal, Integer.parseInt(page), Integer.parseInt(size));
////        return new ResponseEntity<List<Venda>>(lista, OK);
////    }
////
//}