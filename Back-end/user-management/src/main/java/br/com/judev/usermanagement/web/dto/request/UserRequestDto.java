package br.com.judev.usermanagement.web.dto.request;

import br.com.judev.usermanagement.entity.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    @Size(min = 5, max =100)
    private String name;
    @NotBlank
    private String login;
    @NotBlank
    @Size(min = 6, max =10)
    private String password;
    @NotBlank
    @Email(message = "Invalide format !", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

 /*   public UserRequestDto(User entity){
        BeanUtils.copyProperties(entity, this);
    }*/
}
