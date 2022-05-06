package com.business.portfolio.repository;


import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    @Query("{tickerSymbol: ?0}")
    Portfolio findByTickerSymbol(String tickerSymbol);
}
