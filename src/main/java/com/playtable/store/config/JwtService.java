package com.playtable.store.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public OwnerTokenInfo extractUser(String token){
        Claims claims = extractAllClaims(token);
        return OwnerTokenInfo.builder()
                .id(UUID.fromString(claims.get("id", String.class)))
                .name(claims.get("name", String.class))
                .role(claims.get("role", String.class))
                .build();
    }

    private Claims extractAllClaims(String token) {
        System.out.println("---------------------" + secret);
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parse(token)
                .getBody();

    }
}