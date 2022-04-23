package com.business.portfolio.exception;


import com.business.portfolio.model.ErrorEnvelope;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEnvelope generalException(Exception ex, WebRequest request) {
        return ErrorEnvelope.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).description(ex.getMessage()).build();
    }

    @ExceptionHandler(value = {AccessDeniedException.class, ExpiredJwtException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorEnvelope accessDeniedException(Exception ex, WebRequest request) {
        return ErrorEnvelope.builder().status(HttpStatus.UNAUTHORIZED.toString()).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).description(ex.getMessage()).build();
    }
}
