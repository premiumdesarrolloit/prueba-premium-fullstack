package com.eduar.dev.prueba.services.authentication;

import com.eduar.dev.prueba.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value(value = "${jwt.secret.key}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME_TOKEN = 1800000L; // 30 minutos

    public JwtService() {
    }

    //Metodo para generar el token
    public String generateToken(User userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails, EXPIRATION_TIME_TOKEN);
    }

    //Metodos para obtener token y valores de ese token
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String generateAccessToken(Map<String, Object> extraClaims, User userDetails, long expirationInMs) {
        Date timeExpiration = new Date(System.currentTimeMillis() + expirationInMs);
        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("role", userDetails.getAuthorities())
                .subject(userDetails.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(timeExpiration)
                .signWith(getSignInKey())
                .compact();
    }
    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
