package com.business.portfolio.controller;


import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/")
    public ResponseModel getTrades() {
        return tradeService.fetchTrades();
    }
}
