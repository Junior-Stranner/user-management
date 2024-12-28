package br.com.judev.usermanagement.infra.security;

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

    @Autowired
    private TokenService tokenService;

  /*  @Autowired
    private UserRepository userRepository;*/

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        try {
            String login = tokenService.validateToken(token); // Valida o token e recupera o login

            if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Recupera os detalhes do usuário a partir do login
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

                // Cria um objeto de autenticação e define no contexto de segurança
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (RuntimeException e) {
            // Define o status da resposta e escreve a mensagem de erro
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}");
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
 /*   private String recoverToken(HttpServletRequest request) {
       /* String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new RuntimeException("Authorization header missing or invalid");
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header missing or invalid");
        }
        String token = authorizationHeader.substring(7); // Remove "Bearer "

        return token;
    }*/

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");

        // Se o cabeçalho de autorização estiver ausente, retorna null
        if(authHeader == null) return null;

        // Remove a parte "Bearer " do valor do cabeçalho de autorização e retorna o token JWT
        return authHeader.replace("Bearer ", "");
    }

}
