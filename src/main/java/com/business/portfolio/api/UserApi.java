package com.business.portfolio.api;

import com.business.portfolio.auth.util.JwtUtil;
import com.business.portfolio.entity.User;
import com.business.portfolio.model.ResponseModel;
import com.business.portfolio.repository.UserRepo;
import com.business.portfolio.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@Slf4j
@Service("userService")
public class UserApi implements UserService {

    @Autowired
    private ResponseModel response;

    @Autowired
    private UserRepo userRepo;


    @Override
    public ResponseModel userAuthentication(String username, String password, HttpServletResponse servletResponse) {
        boolean isPasswordMatches = false;
        ArrayList<String> jwt = null;
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        try {
            jwt = new ArrayList<>();
            User user = userRepo.findByUserName(username);
            if (user == null)
                throw new AccessDeniedException("No User found for authentication");
            if (user.getUsername().equals(username)) {
                String passCode = user.getPassword();
                isPasswordMatches = bcrypt.matches(password, passCode);
            }
            if (isPasswordMatches) {
                String jwtToken = JwtUtil.addToken(user, servletResponse);
//                String jwtToken = JwtUtil.addToken(authentication, user, servletResponse);
                String expireTime = "Expires_At: " + new Date(System.currentTimeMillis() + 3600000);
                jwt.add(jwtToken);
                jwt.add(expireTime);
                log.info("Successful login for this user {} at {}", username, new Date().getTime());
                response.setStatusCode(HttpStatus.OK);
                response.setMessage("Successful login");
                response.setDetails(jwt);
                return response;
            }
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.setMessage("Wrong Login Attempt!");
            response.setDetails(null);
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AccessDeniedException("Invalid Login", e);
        }
    }
}
