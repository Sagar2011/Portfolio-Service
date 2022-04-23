package com.business.portfolio.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEnvelope {
    private String status;
    private String message;
    private String description;
}
