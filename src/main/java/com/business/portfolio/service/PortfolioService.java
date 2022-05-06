package com.business.portfolio.service;


import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.model.ResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("portfolioService")
public interface PortfolioService {
    ResponseModel addPortfolio(Portfolio portfolio);
    ResponseModel fetchPortfolio();
    ResponseModel fetchReturns();
    List<String> fetchAvailableTickers();
    Portfolio fetchPortfolioBySymbol(String tickerSymbol);
    Portfolio updatePortfolio(Portfolio portfolio);
}
