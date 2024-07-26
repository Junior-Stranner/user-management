package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    /**
     * Filtra as requisições recebidas para autenticar os usuários com base em um token JWT.
     *
     * @param request  A requisição HTTP.
     * @param response A resposta HTTP.
     * @param filterChain O filtro chain.
     * @throws ServletException Se ocorrer um erro específico de servlet.
     * @throws IOException Se ocorrer um erro de entrada ou saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token da requisição
        var token = this.recoverToken(request);

        // Valida o token e recupera o login
        var login = tokenService.validateToken(token);

        if (login != null) {
            // Recupera os detalhes do usuário e define a autenticação no contexto de segurança
            UserDetails user = userRepository.findByEmail(login);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua o filtro chain
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho Authorization da requisição.
     *
     * @param request A requisição HTTP.
     * @return O token JWT, ou null se o cabeçalho Authorization não estiver presente.
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        } else {
            return authHeader.replace("Bearer ", "");
        }
    }
}
