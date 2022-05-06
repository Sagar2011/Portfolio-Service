package com.business.portfolio.controller;


import com.business.portfolio.entity.Trade;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/")
    public ResponseModel getTrades() {
        return tradeService.fetchTrades();
    }

    @PostMapping("/")
    public ResponseModel addTrades(@RequestBody Trade trade) {
        return tradeService.addTrade(trade);
    }

    @DeleteMapping("/{id}")
    public ResponseModel addTrades(@PathVariable String id) {
        return tradeService.removeTrade(id);
    }

    @PutMapping("/{id}")
    public ResponseModel updateTrade(@PathVariable String id, @RequestBody Trade newTrade) {
        return tradeService.updateTrade(id, newTrade);
    }
}
