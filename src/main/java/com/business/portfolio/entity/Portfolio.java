package com.business.portfolio.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("portfolio")
@Data
@ToString
public class Portfolio {

    @Id
    private String id;

    @Indexed(unique = true)
    private String tickerSymbol;
    private double avgBuyPrice;
    private long quantity;
}
