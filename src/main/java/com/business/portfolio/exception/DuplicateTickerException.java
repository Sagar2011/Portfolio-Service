package com.business.portfolio.exception;

public class DuplicateTickerException extends RuntimeException {

    public DuplicateTickerException(String message) {
        super(message);
    }
}
