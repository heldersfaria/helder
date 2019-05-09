package com.hiring.helder.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "disco")
public abstract class Disco {

    @Id
    private String id;

    private Double preco;

    private String nome;

    private String genero;
}
