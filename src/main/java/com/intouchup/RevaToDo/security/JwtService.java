package com.intouchup.RevaToDo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Transactional
public class JwtService {
    @Value("${application.security.jwt.secret_key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh_token.secret_key}")
    private String refreshTokenSecretKey;

    @Value("${application.security.jwt.refresh_token.expiration}")
    private long refreshTokenExpiration;

    public String extractUsername(String token, boolean isRefreshJwtToken) {
        return extractClaim(token, Claims::getSubject, isRefreshJwtToken);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver, boolean isRefreshJwtToken) {
        final Claims claims = extractAllClaims(token, isRefreshJwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, boolean isRefreshJwtToken) {
        Key signInKey = getSignInKey(isRefreshJwtToken);

       token = normalizeToken(token);

        return Jwts.parser()
                .verifyWith((SecretKey) signInKey)          // SecretKey (HMAC)
                .build()
                .parseSignedClaims(token)       // signed JWT (JWS)
                .getPayload();                  // Claims
    }
    private String normalizeToken(String token) {
        if (token == null) throw new JwtException("Missing token");
        token = token.trim();

        // remove accidental template braces
        if (token.startsWith("{{") && token.endsWith("}}")) {
            token = token.substring(2, token.length() - 2).trim();
        }

        // hard reject any remaining braces
        if (token.indexOf('{') >= 0 || token.indexOf('}') >= 0) {
            throw new JwtException("Malformed token (contains braces)");
        }

        return token;
    }
//to be used if no claims specified
    public String generateToken(UserDetails userDetails, boolean isRefreshJwtToken) throws ParseException {
        return generateToken(new HashMap<>(), userDetails, isRefreshJwtToken);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, boolean isRefreshJwtToken) throws ParseException {

        return buildToken(extraClaims, userDetails
                , isRefreshJwtToken
        );
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            boolean isRefreshJwtToken) throws ParseException {
        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        long tokenExpiration = isRefreshJwtToken ? refreshTokenExpiration : jwtExpiration;
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                 .issuedAt(new Date(System.currentTimeMillis()))
.setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
.claim("authorities", authorities) //add claims
                .signWith(getSignInKey(isRefreshJwtToken))
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails, boolean isRefreshJwtToken) throws ParseException {
        final String username = extractUsername(token, isRefreshJwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, isRefreshJwtToken));
    }

    private boolean isTokenExpired(String token, boolean isRefreshJwtToken) throws ParseException {
        Date expirationDate = extractExpiration(token, isRefreshJwtToken);
        Date dateNow = new Date();
        return expirationDate.before(dateNow);
    }

    private Date extractExpiration(String token, boolean isRefreshJwtToken) {
        return extractClaim(token, Claims::getExpiration, isRefreshJwtToken);
    }

    private Key getSignInKey(boolean isRefreshJwtToken) {
        String jwtSecreteKey = isRefreshJwtToken ? refreshTokenSecretKey : secretKey;
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecreteKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
