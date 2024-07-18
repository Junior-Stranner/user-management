package br.com.judev.usermanagement.web.dto.request;

import br.com.judev.usermanagement.entity.User;
import jakarta.persistence.Column;
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
public class UserRequestDto {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;
    @NotBlank

    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Size(min = 6, max =10)
    @Column(nullable = false , length = 100)
    private String password;

    @NotBlank
    @Column(nullable = false , length = 200)
    @Email(message = "Invalide format !", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

 /*   public UserRequestDto(User entity){
        BeanUtils.copyProperties(entity, this);
    }*/
}
