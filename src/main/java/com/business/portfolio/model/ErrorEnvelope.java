package com.business.portfolio.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorEnvelope {
    private String status;
    private String message;
    private String description;
}
