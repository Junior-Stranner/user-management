package br.com.judev.usermanagement.infra.security;

import br.com.judev.usermanagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        try {
            token = recoverToken(request);

            // Valida o token e recupera o login
            String login = tokenService.validateToken(token);

            if (login != null) {
                // Recupera os detalhes do usuário a partir do login
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);
              //  var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                // Cria um objeto de autenticação e define no contexto de segurança
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (RuntimeException e) {
            // Define o status da resposta e escreve a mensagem de erro
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return; // Não continua a cadeia de filtros
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
    // Método para recuperar o token do cabeçalho Authorization
    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new RuntimeException("Authorization header missing or invalid");
        }
    }

}
