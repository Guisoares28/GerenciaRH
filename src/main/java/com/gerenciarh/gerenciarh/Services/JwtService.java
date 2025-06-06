package com.gerenciarh.gerenciarh.Services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gerenciarh.gerenciarh.Models.TokenEntity;
import com.gerenciarh.gerenciarh.Models.User;

@Service
public class JwtService {

	
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;


    public TokenEntity gerarToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        Instant agora = Instant.now();
        Instant expiracao = agora.plus(1, ChronoUnit.DAYS);

        String token = JWT.create()
                .withSubject(user.getNickname())
                .withIssuer("gerenciaRH")
                .withExpiresAt(Date.from(expiracao))
                .sign(algorithm);

        return new TokenEntity(token, user);
    }

    public String pegarNicknameDoToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("gerenciaRH")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    public boolean validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        try{
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException ex){
            return false;
        }
    }

}
