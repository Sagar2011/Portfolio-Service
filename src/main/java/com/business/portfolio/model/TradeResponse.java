package com.business.portfolio.model;


import com.business.portfolio.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeResponse {
    private String tickerSymbol;
    private List<Trade> trades;
}
