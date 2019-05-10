package com.hiring.helder.controllers;

import com.hiring.helder.Resources.VendaResource;
import com.hiring.helder.exceptions.CashBackException;
import com.hiring.helder.exceptions.DiscoVinilException;
import com.hiring.helder.exceptions.VendaException;
import com.hiring.helder.models.Venda;
import com.hiring.helder.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class VendasController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> find(@RequestParam("dataInicial")
                                            @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate dataInicial,
                                            @RequestParam("dataFinal")
                                            @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate dataFinal,
                                            @RequestParam(value = "page", required = false, defaultValue = "0") String page,
                                            @RequestParam(value = "size", required = false, defaultValue = "50") String size) {
        List<Venda> lista = vendaService.find(dataInicial, dataFinal, Integer.parseInt(page), Integer.parseInt(size));
        return new ResponseEntity<List<Venda>>(lista, OK);
    }

    @GetMapping(value = "/vendas/{id}")
    public ResponseEntity<Venda> findById(@PathVariable("id") String id) throws VendaException {
        Venda venda = vendaService.findById(id);
        return new ResponseEntity<Venda>(venda, OK);
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaResource venda) throws VendaException, CashBackException, DiscoVinilException {
        return new ResponseEntity<Venda>(vendaService.processarVenda(venda), OK);
    }
}