package com.business.portfolio.repository;

import com.business.portfolio.entity.Portfolio;
import com.business.portfolio.entity.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends MongoRepository<Trade, String > {
    @Query("{tickerSymbol: ?0}")
    List<Trade> findByTickerSymbol(String tickerSymbol);
}
