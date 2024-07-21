package br.com.judev.usermanagement.web.dto.response;

import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"userId", "name", "login", "password", "email"})
public class UserResponseDto  {

    private Long userId;
    private String name;
    private String login;
    private String password;
    private String email;

}
