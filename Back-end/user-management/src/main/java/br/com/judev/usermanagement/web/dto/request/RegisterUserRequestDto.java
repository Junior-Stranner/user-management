package br.com.judev.usermanagement.web.dto.request;

import br.com.judev.usermanagement.entity.UserRole;
import br.com.judev.usermanagement.exception.CpfCnpj;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterUserRequestDto {

/*    @NotNull(message = "User ID cannot be null")
    private Long userId;*/

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    private String name;

    @NotBlank(message = "CPF/CNPJ cannot be null")
    @Size(min = 11, max = 12)
    @CpfCnpj
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF or CNPJ must have 11 or 14 digits.")
    String cpfCnpj;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must have at least 8 characters, " + "one uppercase letter, one lowercase letter and one number. Ex: Password123")
    private String password;

    @NotBlank
    @Email(message = "Invalide format !", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    private UserRole role;

 /*   public ProfileUserDto(ProfileUser profileUser) {
        BeanUtils.copyProperties(profileUser, this);
        if(profileUser != null && profileUser.getUser() != null) {
            this.user = new UsuarioDTO(profileUser.getUser());
        }
        if(profileUser != null && profileUser.getProfile() != null) {
            this.profile = new PerfilDTO(profileUser.getProfile());
        }
    }
  */
}
