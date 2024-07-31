package br.com.judev.usermanagement.web.dto.response;

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
public class UserResponseDto {

    private String name;
    String cpfCnpj;
    private String password;
    private String email;

}
