package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;


    // Método para gerar um token JWT com base no usuário fornecido
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("login-auth-api") // Mude aqui para corresponder ao issuer do validateToken
                    .withSubject(user.getEmail())
                    .withExpiresAt(toExpireDateTime())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException("ERROR: Token was not generated", exception);
        }
    }

    // Método para validar um token JWT fornecido
    // Método para validar um token JWT fornecido
    public String validateToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new RuntimeException("INVALID TOKEN: Token is null or empty");
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("INVALID TOKEN: Verification failed", e);
        }
    }
    // Método para gerar a data de expiração do token (10 minutos a partir do momento atual)
    private Instant toExpireDateTime() {
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.ofHours(-3));
    }
}
