package com.business.portfolio.api;

import com.business.portfolio.constant.TradeAction;
import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.entity.Trade;
import com.business.portfolio.exception.NoTradeFoundException;
import com.business.portfolio.exception.NotSufficientException;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.model.TradeResponse;
import com.business.portfolio.repository.TradeRepository;
import com.business.portfolio.service.PortfolioService;
import com.business.portfolio.service.TradeService;
import com.business.portfolio.service.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service("tradeService")
@Slf4j
public class TradeApi implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PortfolioService portfolioApi;

    @Autowired
    private UtilsService utilsService;

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

    @Override
    public ResponseModel addTrade(Trade trade) {
        Portfolio portfolio = portfolioApi.fetchPortfolioBySymbol(trade.getTickerSymbol());
        if (portfolio != null && utilsService.validateSecurity(portfolio, trade.getQuantity())) {
            trade.setId(UUID.randomUUID().toString());
            if (trade.getAction().equals(TradeAction.BUY)) {
                portfolio.setAvgBuyPrice(utilsService.calSecurity(portfolio, trade.getQuantity(), trade.getPrice()));
                portfolio.setQuantity(portfolio.getQuantity() + trade.getQuantity());
            } else if (trade.getAction().equals(TradeAction.SELL)) {
                trade.setPrice(100);
                portfolio.setQuantity(portfolio.getQuantity() - trade.getQuantity());
            } else {
                throw new NotSufficientException("Not Sufficient shares to make trade!!");
            }
            //update portfolio
            portfolio = portfolioApi.updatePortfolio(portfolio);
            trade = tradeRepository.save(trade);

        }
        return ResponseModel.builder().statusCode(HttpStatus.CREATED).message("Success!").response(List.of(trade)).build();
    }

    @Override
    public ResponseModel removeTrade(String id) {
        log.info("removing trade id {}", id);
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            Portfolio portfolio = portfolioApi.fetchPortfolioBySymbol(trade.get().getTickerSymbol());
            if (portfolio != null && utilsService.validateSecurity(portfolio, trade.get().getQuantity())) {
                if (trade.get().getAction().equals(TradeAction.BUY)) {
                    portfolio.setAvgBuyPrice(utilsService.removeSecurity(portfolio, trade.get().getQuantity(), trade.get().getPrice()));
                    portfolio.setQuantity(portfolio.getQuantity() - trade.get().getQuantity());
                } else {
                    portfolio.setQuantity(portfolio.getQuantity() + trade.get().getQuantity());
                }
            } else {
                throw new NotSufficientException("Not sufficient quality to remove trade " + id);
            }
            portfolioApi.updatePortfolio(portfolio);
            log.info("successfully removed trade id {}", id);
            return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").response(List.of(tradeRepository.save(trade.get()))).build();
        } else {
            throw new NoTradeFoundException("No trade found for the id " + id);
        }
    }

    @Override
    public ResponseModel updateTrade(String id, Trade newTrade) {
        log.info("Updating trade id {}, with new Trade as {}", id, newTrade.toString());
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            if (!trade.get().getAction().equals(newTrade.getAction())) {
                Portfolio portfolio = portfolioApi.fetchPortfolioBySymbol(trade.get().getTickerSymbol());
                if (portfolio != null && utilsService.validateSecurity(portfolio, newTrade.getQuantity())) {
                    if (newTrade.getAction().equals(TradeAction.BUY)) {
                        portfolio.setAvgBuyPrice(utilsService.calSecurity(portfolio, newTrade.getQuantity(), newTrade.getPrice()));
                        portfolio.setQuantity(portfolio.getQuantity() + newTrade.getQuantity());
                    } else {
                        portfolio.setQuantity(portfolio.getQuantity() - newTrade.getQuantity());
                    }
                } else {
                    throw new NotSufficientException("Not sufficient quality to update trade " + id);
                }
                portfolioApi.updatePortfolio(portfolio);
                log.info("Successfully updated data in trading for id {}", id);
                return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").response(List.of(tradeRepository.save(updateTradeData(newTrade, trade.get())))).build();
            }
        } else {
            throw new NoTradeFoundException("No trade found for the id " + id);
        }
        return null;
    }

    private Trade updateTradeData(Trade newTrade, Trade oldTrade) {
        oldTrade.setPrice(newTrade.getPrice());
        oldTrade.setQuantity(newTrade.getQuantity());
        oldTrade.setAction(newTrade.getAction());
        return oldTrade;
    }
}
