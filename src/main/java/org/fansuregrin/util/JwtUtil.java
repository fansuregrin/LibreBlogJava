package org.fansuregrin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;

    public JwtUtil(@Value("${libreblog.token.secret}") String secret) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwt(Map<String, Object> claims) {
        return Jwts.builder().signWith(SECRET_KEY).claims(claims)
            .compact();
    }

    public Claims parseJwt(String jwt) {
        return (Claims) Jwts.parser().verifyWith(SECRET_KEY).build()
            .parse(jwt).getPayload();
    }
}
