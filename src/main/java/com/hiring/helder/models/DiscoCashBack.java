package com.hiring.helder.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "disco")
public class DiscoCashBack extends Disco {

    private Double valorComCashBack;

}
