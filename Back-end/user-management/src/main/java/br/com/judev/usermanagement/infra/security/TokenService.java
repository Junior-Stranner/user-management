package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private static final String ISSUER = "user-management";
    private static final int DEFAULT_EXPIRATION_HOURS = 2;
    private static final int TEMPORARY_TOKEN_MINUTES = 15;

    // Método para gerar token padrão
    public String generateToken(User user) {
        return generateToken(user.getEmail(), DEFAULT_EXPIRATION_HOURS);
    }

    // Método para gerar token temporário (ex.: recuperação de senha)
    public String generateTemporaryTokenToRecoveryPassword(User user) {
        return generateToken(user.getEmail(), TEMPORARY_TOKEN_MINUTES / 60.0); // 15 minutos
    }

    // Método interno para geração de token com expiração variável
    private String generateToken(String subject, double expirationHours) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            LocalDateTime expirationTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
                    .plusMinutes((long) (expirationHours * 60)); // Usando o fuso horário de São Paulo
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(subject)
                    .withExpiresAt(expirationTime.toInstant(ZoneOffset.ofHours(-3))) // Ajustado para horário local
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token: " + e.getMessage(), e);
        }
    }

    // Método para validar token e retornar o subject
    public String validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("O token informado é nulo ou está vazio.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException("Falha na validação do token: " + e.getMessage());
        }
    }
}
