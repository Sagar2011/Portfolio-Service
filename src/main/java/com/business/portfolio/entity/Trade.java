package com.business.portfolio.entity;


import com.business.portfolio.constant.TradeAction;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("trade")
@Data
public class Trade {

    @Id
    private String id;
    @Indexed
    private String tickerSymbol;
    private TradeAction action;
    private long quantity;
    private double price;
}
