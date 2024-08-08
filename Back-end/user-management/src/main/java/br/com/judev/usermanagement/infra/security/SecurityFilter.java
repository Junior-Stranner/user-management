package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token da requisição
        var token = this.recoverToken(request);

        // Valida o token e recupera o login
        var login = tokenService.validateToken(token);

        if (login != null) {
            // Recupera os detalhes do usuário a partir do login
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);

            // Cria um objeto de autenticação e define no contexto de segurança
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
