package com.business.portfolio.auth.filter;

import com.business.portfolio.auth.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class AuthFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        ArrayList<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        chain.doFilter(request, response);

    }
}
