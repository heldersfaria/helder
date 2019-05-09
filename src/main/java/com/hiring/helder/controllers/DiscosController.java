package com.hiring.helder.controllers;

import com.hiring.helder.models.DiscoVinil;
import com.hiring.helder.services.DiscoVinilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class DiscosController {

    @Autowired
    private DiscoVinilService discoVinilService;

    @GetMapping("/discos")
    public ResponseEntity<List<DiscoVinil>> find(@RequestParam(value = "genero", required = false) String genero,
                                                 @RequestParam(value = "page", required = false, defaultValue = "0") String page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "50") String size) {

        List<DiscoVinil> lista = discoVinilService.find(genero, Integer.parseInt(page), Integer.parseInt(size));
        return new ResponseEntity<List<DiscoVinil>>(lista, OK);
    }

    @GetMapping("/discos/{id}")
    public ResponseEntity<DiscoVinil> findById(@PathVariable("id") String id) {
        return new ResponseEntity<DiscoVinil>(discoVinilService.findById(id), OK);
    }
}