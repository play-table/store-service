package com.playtable.store.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace("Bearer ", "");
        MemberTokenInfo memberTokenInfo;

        try{
            memberTokenInfo = jwtService.extractUser(token);
        } catch (Exception ignored){
            memberTokenInfo = null;
        }

        if(memberTokenInfo != null &&
                !memberTokenInfo.getId().toString().isEmpty() &&
                SecurityContextHolder.getContext().getAuthentication() ==null
        ){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    memberTokenInfo, null, memberTokenInfo.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);

    }}
