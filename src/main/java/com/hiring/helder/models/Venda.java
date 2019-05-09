package com.hiring.helder.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "Venda")
public class Venda {

    @Id
    private String id;

    private LocalDate data;

    private Double valorTotalCashBack;

    @DBRef(db = "helder")
    private List<DiscoCashBack> discos;
}
