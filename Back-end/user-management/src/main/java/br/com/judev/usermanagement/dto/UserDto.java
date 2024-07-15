package br.com.judev.usermanagement.dto;

import br.com.judev.usermanagement.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserDto {
    private Long id;
    @NotBlank
    @Size(min = 5, max =100)
    private String name;
    private String login;
    @Size(min = 6, max =10)
    private String password;
    @Email(message = "Invalide format !", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    public UserDto(User entity){
        BeanUtils.copyProperties(entity, this);
    }
}
