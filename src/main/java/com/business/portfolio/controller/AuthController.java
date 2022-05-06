package com.business.portfolio.controller;


import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel loginWithCredentials(@RequestHeader String username, @RequestHeader String password, HttpServletResponse response) {
        try {
            return userService.userAuthentication(username, password, response);
        } catch (Exception exception) {
            log.error("Exception occurred in the login API {}", exception.getMessage());
            throw exception;
        }
    }
}
