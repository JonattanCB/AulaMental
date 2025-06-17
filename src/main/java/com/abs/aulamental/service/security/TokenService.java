package com.abs.aulamental.service.security;

import com.abs.aulamental.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateTocken(Usuario usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create().withIssuer("aulamental")
                    .withSubject(usuario.getCorreo())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(getExpiration())
                    .sign(algorithm);
        }catch (Exception e){
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String getSubject(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm).withIssuer("aulamental").build().verify(token).getSubject();
        }catch (Exception e){
            throw new RuntimeException("Error getting subject from token", e);
        }
    }

    private Instant getExpiration() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-05:00"));
    }
}
