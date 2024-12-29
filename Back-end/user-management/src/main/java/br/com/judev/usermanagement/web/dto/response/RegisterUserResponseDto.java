package br.com.judev.usermanagement.web.dto.response;

import br.com.judev.usermanagement.entity.UserRole;
import br.com.judev.usermanagement.exception.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserResponseDto  {
    private Long userId;
    private String name;
    private String cpfCnpj;
    private String password;
    private String email;
    private UserRole role;

}
