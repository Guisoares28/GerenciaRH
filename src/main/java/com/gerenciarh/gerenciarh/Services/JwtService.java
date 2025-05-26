package com.gerenciarh.gerenciarh.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gerenciarh.gerenciarh.Exceptions.UnauthorizedException;
import com.gerenciarh.gerenciarh.Models.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    Dotenv dotenv = Dotenv.load();

    public Dotenv getDotenv() {
        return dotenv;
    }

    public String gerarToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY"));
        Instant agora = Instant.now();
        Instant expiracao = agora.plus(1, ChronoUnit.DAYS);
        return JWT.create()
                .withSubject(user.getNickname())
                .withIssuer("gerenciaRH")
                .withExpiresAt(Date.from(expiracao))
                .sign(algorithm);
    }

    public String pegarNicknameDoToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY"));
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("gerenciaRH")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    public boolean validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY"));
        try{
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException ex){
            return false;
        }
    }

}
