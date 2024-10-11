package com.VidaPlus.VitaValore.infra.security;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.Users;
import com.VidaPlus.VitaValore.models.enums.Role;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.token.secret}")
    private String secret;
    public String generateToken(@NotNull String email, @NotNull Role role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("VitaValore")
                    .withClaim("email", email)
                    .withClaim("role", role.name())
                    .withSubject(email)
                    .withExpiresAt(this.generateExpirationDate())
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


    private Instant generateExpirationDate() {

        return LocalDateTime.now().plusDays(15).toInstant(ZoneOffset.of("-03:00"));

    }

    public SecurityDto getInfo(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("VitaValore")
                    .build()
                    .verify(token);
            String email = jwt.getSubject();
            Role role = Role.valueOf(jwt.getClaim("role").asString());
            return new SecurityDto(role,email);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public Optional<?> verification(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("VitaValore")
                    .build()
                    .verify(token);

            String email = jwt.getSubject();
            Role role = Role.valueOf(jwt.getClaim("role").asString());
            System.out.println(role.name());

            return switch (role) {
                case EMPRESA -> empresasRepository.findByEmail(email);
                case ADMIN -> Optional.empty();
                case USER -> userRepository.findByEmail(email);
                default -> Optional.empty();
            };
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou erro ao decifrá-lo: " + exception.getMessage(), exception);
        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Role não encontrado no token: " + e.getMessage(), e);
        }
    }
}