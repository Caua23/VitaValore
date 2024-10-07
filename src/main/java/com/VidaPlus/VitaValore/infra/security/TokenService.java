package com.VidaPlus.VitaValore.infra.security;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private EmpresasRepository empresasRepository;

    @Value("${jwt.token.secret}")
    private String secret;
    public String generateToken(@NotNull Empresas empresas) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("VitaValore")
                    .withSubject(empresas.getEmail())
                    .withExpiresAt(this.generedExpirationDate())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro while authenticating user", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("VitaValore")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }


    private Instant generedExpirationDate() {

        return LocalDateTime.now().plusDays(15).toInstant(ZoneOffset.of("-03:00"));

    }


    public Optional<Empresas> verification(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("VitaValore")
                    .build()
                    .verify(token);
            String email = jwt.getSubject();
            Optional<Empresas> empresa = empresasRepository.findByEmail(email);

            return empresa;
        }catch (JWTVerificationException exception) {
            System.out.println("Token inválido ou erro ao decifrá-lo: " + exception.getMessage());
            return Optional.empty();
        }

}

}