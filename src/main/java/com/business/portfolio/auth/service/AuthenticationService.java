package com.business.portfolio.auth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthenticationService {
    private static final String SigningKey = "POC_CCM";

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        log.debug("token is: {}", token);
        if (token != null) {
            String user;
            try {
                Claims claims = Jwts.parser().setSigningKey(SigningKey).parseClaimsJws(token.replace("Bearer ", "")).getBody();
                user = claims.get("un", String.class);
                if (user != null) {
                    String roles = claims.get("roles", String.class);
                    List<GrantedAuthority> grantedAuths =
                            AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
                    log.info("roles::::::" + grantedAuths);
//                    boolean authorized = grantedAuths.contains(new SimpleGrantedAuthority("ADMIN"));
                    return new UsernamePasswordAuthenticationToken(user, null, grantedAuths);
                }
            } catch (ExpiredJwtException expiredException) {
                log.error("Expired Exception");
            } catch (Exception exception) {
                log.error("Jwt Token Error : {} ", exception.getMessage());
            }
        }
        return null;
    }
}
