package br.com.judev.usermanagement.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {
    @NotBlank
    @Size(min = 6, max = 10)
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 10)
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 10)
    private String confirmPassword;

}
