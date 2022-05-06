package com.business.portfolio.service;


import com.business.portfolio.entity.Portfolio;
import org.springframework.stereotype.Service;

@Service
public class UtilsService {

    public double calSecurity(Portfolio portfolio, long quantity, double priceBuy) {
        return ((portfolio.getAvgBuyPrice() * portfolio.getQuantity()) + (quantity * priceBuy)) / (portfolio.getQuantity() + quantity);
    }

    public boolean validateSecurity(Portfolio portfolio, long quantity) {
        return portfolio.getQuantity() - quantity >= 0L;
    }

    public double removeSecurity(Portfolio portfolio, long quantity, double priceBuy) {
        return ((portfolio.getAvgBuyPrice() * portfolio.getQuantity()) - (quantity * priceBuy)) / (portfolio.getQuantity() - quantity);
    }
}
