package br.com.judev.usermanagement.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordRecoveryDto {

    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(min = 6, max = 8)
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 8)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must have between 6 until 8 characters, " +
            "one uppercase letter, one lowercase letter and one number. Ex: Pass1234")
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 8)
    private String confirmPassword;

}
