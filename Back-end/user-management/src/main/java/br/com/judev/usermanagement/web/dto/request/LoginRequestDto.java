package br.com.judev.usermanagement.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Invalid Email")
    String email;

    @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF or CNPJ must have 11 or 14 digits.")
    String cpfCnpj;

    @NotBlank(message = "Password cannot be white or null ")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must have at least 8 characters, " + "one uppercase letter, one lowercase letter and one number. Ex: Password123")
    String password;
}
