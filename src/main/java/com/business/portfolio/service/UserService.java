package com.business.portfolio.service;


import com.business.portfolio.model.ResponseModel;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface UserService {
    ResponseModel userAuthentication(String username, String password, HttpServletResponse response);

}
