package br.com.judev.usermanagement.infra.config;

import br.com.judev.usermanagement.infra.security.SecurityFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
 //   private final UserDetailsService userDetailsService;
 //   private final PasswordEncoder passwordEncoder;
 //   private final AuthenticationProvider authenticationProvider;


    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
   //     this.authenticationProvider = authenticationProvider;
    }


    /*   @Autowired
    DataSource dataSource;*/

 /*   private static final String[] PERMIT_ALL= {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/h2-console/**"
    };*/

    private static final String[] PUBLIC_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/h2-console/**"
    };

    private static final String[] AUTH_PATHS = {
            "/auth/login",
            "/auth/register",
            "/auth/confirm-code"
    };

    /*@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(PERMIT_ALL).permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/confirmCode").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler()))
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();

}*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita CSRF pois usaremos tokens JWT
                .csrf(csrf -> csrf.disable())

                // Configura CORS
            //    .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Configura sessão como STATELESS (sem estado)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          //      .authenticationProvider(authenticationProvider)

                // Configura headers de segurança
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // Permite H2 Console
                        .xssProtection(xss -> xss.disable()))       // Proteção contra XSS

                // Configura autorizações de requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_PATHS).permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/**", "/auth/register", "/auth/login").permitAll()
                     //   .requestMatchers(HttpMethod.POST, AUTH_PATHS).permitAll()
                        //.requestMatchers("/auth").permitAll()
                        .anyRequest().authenticated())

                // Configura tratamento de exceções
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                // Adiciona filtro JWT
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

/*    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        };
    }*/


    /*1. Autenticação em Memória:
   @Bean
public UserDetailsService userDetailsService() {
    UserDetails user1 = User.withUsername("user1")
            .password("{noop}password1")  // {noop} significa que a senha não está encriptada
            .roles("USER")
            .build();

    UserDetails admin = User.withUsername("admin")
            .password("{noop}adminPass")
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user1, admin);
    }

    //Autenticação com Banco de Dados (via JdbcUserDetailsManager):
     @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);

        UserDetails user1 = User.withUsername("user1")
                .password("{noop}password1")  // {noop} significa que a senha não está encriptada
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}adminPass")
                .roles("ADMIN")
                .build();

        // Aqui você pode salvar os usuários no banco
         JdbcUserDetailsManager userDetailsManager =
            new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(admin);

        return userDetailsManager;
    }

    JdbcUserDetailsManager userDetailsManager =
            new JdbcUserDetailsManager(dataSource);
             userDetailsManager.createUser(user1);
             userDetailsManager.createUser(admin);
             //Banco de Dados
            return userDetailsManager;
    //EM Memória
    return new InMemoryUserDetailsManager(user1, admin);*/



 /*   @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }*/


}
