package com.business.portfolio.api;

import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.exception.DuplicateTickerException;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.repository.PortfolioRepository;
import com.business.portfolio.service.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("portfolioService")
@Slf4j
public class PortfolioApi implements PortfolioService {

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
            return ResponseModel.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).message("Error in processing").details(List.of((e.getMessage()))).build();
        }
        return ResponseModel.builder().statusCode(HttpStatus.OK).message("Success!").details(List.of(portfolio)).build();
    }
}
