package com.business.portfolio.repository;

import com.business.portfolio.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    @Query("{username: ?0}")
    User findByUserName(String username);
}
