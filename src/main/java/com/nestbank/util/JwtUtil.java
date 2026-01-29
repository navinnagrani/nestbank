package com.nestbank.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String secret = "my-super-secure-jwt-secret-key-256-bit";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String username, String role, Long customerId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .claim("customerId",customerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
