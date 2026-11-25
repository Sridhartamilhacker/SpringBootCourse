package com.apex.wallet.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-ms}")
    private Long expiration;

    private final SecretKey signingKey = getSigningKey();


    public SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",userDetails.getAuthorities());
        return generateToken(claims,userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        Instant now = Instant.now();
        return Jwts.builder().
                claims(extraClaims).
                subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expiration)))
                .signWith(signingKey)
                .compact();
    }


    public Claims extractClaim(String token){
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }

}
