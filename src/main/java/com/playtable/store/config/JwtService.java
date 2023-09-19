package com.playtable.store.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public MemberTokenInfo extractUser(String token){
        Claims claims = extractAllClaims(token);
        return MemberTokenInfo.builder()
                .id(UUID.fromString(claims.get("id", String.class)))
                .username(claims.get("username", String.class))
                .email(claims.get("email", String.class))
                .realName(claims.get("realName", String.class))
                .contact(claims.get("contact", String.class))
                .role(claims.get("role", String.class))
                .nickName(claims.get("nickName", String.class))
                .profileImage(claims.get("profileImage", String.class))
                .build();
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parse(token)
                .getBody();

    }
}