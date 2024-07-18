package br.com.judev.usermanagement.web.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long userId;
    private String name;
    private String login;
    private String password;
    private String email;

}
