package com.jusethag.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // TODO: Save secrets on a vault service
    @Value("jwt.secret")
    private String SIGNATURE_KEY;
    private static final int MILLISECONDS = 1000;
    private static final int SECONDS = 60;
    private static final int MINUTES = 60;
    private static final int HOURS = 10;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + MILLISECONDS * SECONDS * MINUTES * HOURS ))
                .signWith(SignatureAlgorithm.HS256, SIGNATURE_KEY).compact();

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(this.getUsername(token)) && !this.isTokenExpired(token);
    }

    public String getUsername(String token) {
        return this.getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SIGNATURE_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getClaims(token).getExpiration().before(new Date());
    }
}
