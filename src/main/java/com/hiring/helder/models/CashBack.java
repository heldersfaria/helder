package com.hiring.helder.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "CashBack")
public class CashBack {

    @Id
    private String id;

    private List<Double> descontos;
}
