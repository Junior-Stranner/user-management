package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        try {
            // Valida o token e recupera o login (subject)
            if (token != null) {
                String login = tokenService.validateToken(token);

                // Verifica se o usuário está autenticado e define o contexto de segurança
                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                    // Cria o objeto de autenticação e define no contexto de segurança
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (InvalidTokenException e) {
            // Se o token for inválido, retorna erro 401 com a mensagem apropriada
            writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token", e.getMessage());
            return; // Interrompe a cadeia de filtros
        } catch (Exception e) {
            // Tratar outros erros inesperados
            writeErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error", e.getMessage());
            return;
        }

        // Continua a cadeia de filtros caso não haja erro
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho Authorization da requisição.
     *
     * @param request A requisição HTTP.
     * @return O token JWT, ou null se o cabeçalho Authorization não estiver presente.
     */
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho Authorization está presente e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        // Remove o prefixo "Bearer " e retorna o token
        return authHeader.substring(7);
    }

    /**
     * Escreve uma resposta de erro no formato JSON.
     *
     * @param response O objeto HttpServletResponse.
     * @param status   O status HTTP.
     * @param error    Uma breve descrição do erro.
     * @param message  A mensagem detalhada do erro.
     * @throws IOException Caso ocorra um erro ao escrever a resposta.
     */
    private void writeErrorResponse(HttpServletResponse response, int status, String error, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        String jsonResponse = String.format("{\"error\": \"%s\", \"message\": \"%s\"}", error, message);
        response.getWriter().write(jsonResponse);
    }

}
