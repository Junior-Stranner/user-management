package br.com.judev.usermanagement.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserChangePasswordDto {

    @NotBlank(message = "Current password cannot be blank.")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters.")
    private String currentPassword;

    @NotBlank(message = "New password cannot be blank.")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters.")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank.")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters.")
    private String confirmPassword;

}
