package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.InvalidTokenException;
import br.com.judev.usermanagement.repository.UserRepository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${security.token}")
    private String secret;


    private static final String ISSUER = "user-management";

    // Método para gerar um token JWT com base no usuário fornecido
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Chave secreta para assinar o token
            String token = JWT.create()
                    .withIssuer(ISSUER) // Mude aqui para corresponder ao issuer do validateToken
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token; // Retorna o token gerado
        } catch (JWTCreationException exception) {
            throw new RuntimeException("ERROR: Token was not generated", exception);
        }
    }

    public String generateTemporaryTokenToRecoveryPassword (User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-3")))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("ERROR WHILE CREATE TOKEN");
        }
    }

    // Método para validar um token JWT fornecido
    public String validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException("Token verification failed" +e);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
