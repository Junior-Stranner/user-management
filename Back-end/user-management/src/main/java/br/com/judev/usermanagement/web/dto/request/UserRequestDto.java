package br.com.judev.usermanagement.web.dto.request;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.CpfCnpj;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDto extends User {

 /*   @NotNull(message = "User ID cannot be null")
    private Long userId;*/

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    private String name;

    @NotBlank(message = "CPF/CNPJ cannot be null")
    @CpfCnpj
    String cpfCnpj;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters")
    private String password;
    @NotBlank
    @Email(message = "Invalide format !", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

}
