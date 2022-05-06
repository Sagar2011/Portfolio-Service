package com.business.portfolio.service;


import com.business.portfolio.entity.Trade;
import com.business.portfolio.model.ResponseModel;
import org.springframework.stereotype.Service;

@Service("tradeService")
public interface TradeService {
    ResponseModel fetchTrades();
    ResponseModel addTrade(Trade trade);
}
