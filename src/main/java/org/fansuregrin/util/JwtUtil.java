package org.fansuregrin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;
    private final Long EXPIRES_IN;

    public JwtUtil(JwtProperties jwtProperties) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(
            jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.EXPIRES_IN = jwtProperties.getExpiresIn();
    }

    public String generateJwt(Map<String, Object> claims) {

        return Jwts.builder().signWith(SECRET_KEY).claims(claims)
            .expiration(new Date(System.currentTimeMillis() + EXPIRES_IN))
            .compact();
    }

    public Claims parseJwt(String jwt) {
        return (Claims) Jwts.parser().verifyWith(SECRET_KEY).build()
            .parse(jwt).getPayload();
    }
}
