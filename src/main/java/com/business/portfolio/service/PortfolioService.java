package com.business.portfolio.service;


import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.model.ResponseModel;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service("portfolioService")
public interface PortfolioService {
    ResponseModel addPortfolio(Portfolio portfolio);
}
