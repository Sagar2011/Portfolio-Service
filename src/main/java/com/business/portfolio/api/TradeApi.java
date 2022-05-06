package com.business.portfolio.api;

import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.entity.Trade;
import com.business.portfolio.exception.DuplicateTickerException;
import com.business.portfolio.exception.NoHoldingsFoundException;
import com.business.portfolio.exception.NoTradeFoundException;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.model.TradeResponse;
import com.business.portfolio.repository.TradeRepository;
import com.business.portfolio.service.PortfolioService;
import com.business.portfolio.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service("tradeService")
@Slf4j
public class TradeApi implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PortfolioService portfolioApi;

    @Override
    public ResponseModel fetchTrades() {
        List<TradeResponse> tradeResponses = new ArrayList<>();
        List<String> tickers = portfolioApi.fetchAvailableTickers();
        try {
            if (!tickers.isEmpty()) {
                for (String portfolio :
                        tickers) {
                    log.info("Fetching Trade for ticker {}", portfolio);
                    List<Trade> trades = tradeRepository.findByTickerSymbol(portfolio);
                    if (trades.size() > 0) {
                        TradeResponse tradeResponse = TradeResponse.builder().tickerSymbol(portfolio).trades(trades).build();
                        tradeResponses.add(tradeResponse);
                    } else {
                        throw new NoTradeFoundException("No Trade found in the system!");
                    }
                }
            } else {
                throw new NoTradeFoundException("No Trade found in the system!");
            }
        } catch (NoTradeFoundException e) {
            log.error("Cannot found any trades in the DB now!!");
            throw new NoTradeFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Error in fetching trades in the DB with {}", e.getMessage());
            return ResponseModel.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).message("Error in fetching portfolio!").response(List.of((e.getMessage()))).build();
        }
        return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").response(tradeResponses).build();

    }
}
