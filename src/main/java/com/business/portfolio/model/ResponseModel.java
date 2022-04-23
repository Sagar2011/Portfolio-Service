package com.business.portfolio.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private HttpStatus statusCode;
    public String message;
    private ArrayList<?> details;
}
