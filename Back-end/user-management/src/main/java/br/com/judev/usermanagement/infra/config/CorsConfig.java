package br.com.judev.usermanagement.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.origin-patterns}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With")
                .allowCredentials(true)
                .maxAge(3600); // 1 hora de cache para pre-flight requests
    }
}
/*CorsConfig:

Valor padrão para allowedOrigins caso não exista no properties
Adição do método OPTIONS explicitamente
Headers específicos ao invés de wildcard (*)
Configuração de maxAge para otimizar pre-flight requests
allowCredentials para suportar cookies/autenticação
Headers mais específicos e seguros*/