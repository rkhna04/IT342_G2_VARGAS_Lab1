package edu.example.lab1.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private final String secret;
    private final long expirationMs = 7 * 24 * 60 * 60 * 1000L;

    public JwtUtil(String secret) {
        this.secret = secret;
    }

    public String generateToken(Long userId, String email) {
        return Jwts.builder()
                .claim("id", userId)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
