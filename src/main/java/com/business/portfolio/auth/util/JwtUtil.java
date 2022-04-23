package com.business.portfolio.auth.util;

import com.business.portfolio.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    static final long EXPIRATIONTIME = 3600000; // 1 hour in milliseconds
    private static final String SigningKey = "POC_CCM";
    private static final String PREFIX = "Bearer";


    public static String addToken(User user, HttpServletResponse res) {

        Claims claims = Jwts.claims();
        claims.put("un", user.getUserName()); // username
        claims.put("roles", user.getRole()); //user role in system
        String jwtToken = Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SigningKey).compact();
        res.addHeader("Authorization", PREFIX + " " + jwtToken);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        return jwtToken;
    }

}

