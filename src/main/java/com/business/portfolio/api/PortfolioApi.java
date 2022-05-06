package com.business.portfolio.api;

import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.exception.DuplicateTickerException;
import com.business.portfolio.exception.NoHoldingsFoundException;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.repository.PortfolioRepository;
import com.business.portfolio.service.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service("portfolioService")
@Slf4j
public class PortfolioApi implements PortfolioService {

    private static final double currentPrice = 100;

    @Autowired
    private PortfolioRepository portfolioRepository;


    @Override
    public ResponseModel addPortfolio(Portfolio portfolio) {
        log.info("Saving the portfolio in the DB with symbol {}", portfolio.toString());
        portfolio.setId(UUID.randomUUID().toString());
        try {
            Portfolio portfolioTemp = portfolioRepository.findByTickerSymbol(portfolio.getTickerSymbol());
            if (portfolioTemp == null)
                portfolioRepository.save(portfolio);
            else {
                throw new DuplicateTickerException("Duplicate entry of ticker Symbol data");
            }
        } catch (DuplicateTickerException e) {
            log.error("Found duplicate ticker symbol in the portfolio to add with symbol {}", portfolio.getTickerSymbol());
            throw new DuplicateTickerException(e.getMessage());
        } catch (Exception e) {
            log.error("Error while adding portfolio in the DB {} with {}", portfolio, e.getMessage());
            return ResponseModel.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).message("Error in processing").response(List.of((e.getMessage()))).build();
        }
        return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").response(List.of(portfolio)).build();
    }

    @Override
    public ResponseModel fetchPortfolio() {
        log.info("Fetching all the portfolio");
        try {
            List<Portfolio> holdings = portfolioRepository.findAll();
            if (holdings.size() > 0) {
                return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").response(holdings).build();
            } else {
                throw new NoHoldingsFoundException("No portfolio found in current time!");
            }
        } catch (NoHoldingsFoundException e) {
            log.error("Cannot found any portfolio in the DB now!!");
            throw new DuplicateTickerException(e.getMessage());
        } catch (Exception e) {
            log.error("Error in fetching portfolio in the DB with {}", e.getMessage());
            return ResponseModel.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).message("Error in fetching portfolio!").response(List.of((e.getMessage()))).build();
        }
    }

    @Override
    public ResponseModel fetchReturns() {
        log.info("Fetching returns for portfolio");
        try {
            List<Portfolio> holdings = portfolioRepository.findAll();
            if (holdings.size() > 0) {
                return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").response(Collections.singletonList(calculateCumulativeReturn(holdings))).build();
            } else {
                throw new NoHoldingsFoundException("No portfolio found in current time for return!");
            }
        } catch (NoHoldingsFoundException e) {
            log.error("Cannot found any portfolio in the DB now!!");
            throw new DuplicateTickerException(e.getMessage());
        } catch (Exception e) {
            log.error("Error in fetching portfolio in the DB with {}", e.getMessage());
            return ResponseModel.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).message("Error in fetching portfolio!").response(List.of((e.getMessage()))).build();
        }
    }

    @Override
    public List<String> fetchAvailableTickers() {
        log.info("Fetching all the portfolio symbols");
        List<String> response = new ArrayList<>();
        try {
            List<Portfolio> holdings = portfolioRepository.findAll();
            if (holdings.size() > 0) {
                for (Portfolio p :
                        holdings) {
                    response.add(p.getTickerSymbol());
                }
            }
        } catch (Exception e) {
            log.error("Error in fetching portfolio in the DB with {}", e.getMessage());
            return response;
        }
        return response;
    }

    private double calculateCumulativeReturn(List<Portfolio> portfolios) {
        double sum = 0;
        for (Portfolio portfolio :
                portfolios) {
            sum += (currentPrice - portfolio.getAvgBuyPrice()) * portfolio.getQuantity();
        }
        log.info("Portfolio fetch returns calculated as {}", sum);
        return sum;
    }
}
