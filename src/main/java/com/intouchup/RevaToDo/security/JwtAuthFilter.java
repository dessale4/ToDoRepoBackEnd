package com.intouchup.RevaToDo.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().contains("/public/")) {
            log.info("authentication is not required => " + request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String userEmail;
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                jwt = authHeader.substring(7);
            } else if (request.getCookies() != null) {

                jwt = Arrays.stream(request.getCookies())
                        .filter(c -> c.getName().equals("jwt"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null);

            } else {
                log.info("not a jwt Auth => " + request.getServletPath());
                exceptionResolver.resolveException(request, response, null, new RuntimeException("Access not allowed"));
                return;
            }
            if (jwt == null || jwt.isEmpty() || jwt.isBlank()) {
                log.info("jwt not found => " + request.getServletPath());
                exceptionResolver.resolveException(request, response, null, new RuntimeException("Access not allowed"));
                return;
            }
            userEmail = jwtService.extractUsername(jwt, false);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails, false)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    log.info("not a valid jwt");
                    exceptionResolver.resolveException(request, response, null, new RuntimeException("Not a valid jwt token"));
                    return;
                }
            } else if (userEmail == null) {
                log.info("userEmail not found => ");
                exceptionResolver.resolveException(request, response, null, new RuntimeException("Access not allowed"));
                return;
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | SignatureException | ParseException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }

}
