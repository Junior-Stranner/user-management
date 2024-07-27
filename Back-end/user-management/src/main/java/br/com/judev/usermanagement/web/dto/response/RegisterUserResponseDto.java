package br.com.judev.usermanagement.web.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"userId", "name", "login", "password", "email"})
public class RegisterUserResponseDto  {

    private Long userId;
    private String name;
    private String cpfCnpj;
    private String password;
    private String email;

}
