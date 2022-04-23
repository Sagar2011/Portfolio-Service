package com.business.portfolio.auth.config;

import com.business.portfolio.auth.filter.AuthFilter;
import com.business.portfolio.model.ErrorEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    ObjectMapper mapper = new ObjectMapper();

    // For security on api endpoints
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/api/v1/auth/**").permitAll().and().antMatcher("/**").authorizeRequests().anyRequest()
                .authenticated().and().addFilterAfter(new AuthFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests().and().exceptionHandling().authenticationEntryPoint(((request, response, e) -> {
                    ErrorEnvelope errorResponse = new ErrorEnvelope("Access Denied_403", "Not Authorized", Arrays.toString(e.getStackTrace()));
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(mapper.writeValueAsString(errorResponse));
                }));
    }

    // For ignoring security of api endpoints
//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity.ignoring().antMatchers("/api/v1/auth");
//    }

}