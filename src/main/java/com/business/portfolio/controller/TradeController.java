package com.business.portfolio.controller;


import com.business.portfolio.entity.Trade;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel getTrades() {
        return tradeService.fetchTrades();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel addTrades(@RequestBody Trade trade) {
        return tradeService.addTrade(trade);
    }

    @DeleteMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel addTrades(@PathVariable String id) {
        return tradeService.removeTrade(id);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel updateTrade(@PathVariable String id, @RequestBody Trade newTrade) {
        return tradeService.updateTrade(id, newTrade);
    }
}
