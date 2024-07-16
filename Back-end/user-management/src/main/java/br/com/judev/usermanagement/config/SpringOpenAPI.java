package br.com.judev.usermanagement.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringOpenAPI {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("User Management API")
                        .version("v1")
                        .description("Some description about this API")
                        .termsOfService("https://github.com/judev/user-management")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact()
                                .name("Junior Stranner ")
                                .email("Junior-stranner@gmail.user-management.com")));
    }
}
