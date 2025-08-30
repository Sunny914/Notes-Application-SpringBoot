package com.namekart.NotesApp.utils;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "mySecretKey";
    private final int EXPIRATION = 86400000;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return (Jwts.parser().setSigningKey(SECRET)).parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJwt(token);
            return true;
        }catch (JwtException e){
            return false;
        }


    }

}
