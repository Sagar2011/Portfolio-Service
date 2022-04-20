package com.business.portfolio.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("portfolio")
@Data
public class Portfolio {

    @Id
    private UUID id;

}
