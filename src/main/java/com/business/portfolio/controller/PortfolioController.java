package com.business.portfolio.controller;


import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/")
    public String test() {
        return "success";
    }

    @PostMapping("/")
    public ResponseModel addPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.addPortfolio(portfolio);
    }
}
