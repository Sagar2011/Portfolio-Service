package com.business.portfolio.repository;


import com.business.portfolio.entity.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, UUID> {
}
