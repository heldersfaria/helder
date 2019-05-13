package com.hiring.helder.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static com.hiring.helder.constants.StringConstants.DATE_FORMAT;

@Data
@Document(collection = "Venda")
public class Venda {

    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDate data;

    private Double valorTotalCashBack;

    @DBRef(db = "helder")
    private List<DiscoCashBack> discos;
}
