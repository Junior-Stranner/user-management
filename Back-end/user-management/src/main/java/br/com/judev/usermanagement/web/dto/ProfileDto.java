package br.com.judev.usermanagement.web.dto;

import br.com.judev.usermanagement.entity.Profile;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Getter@Setter@ToString
public class ProfileDto extends Profile {

 /*   @NotNull(message = "Prifile ID cannot be null")
    private Long profileId;*/

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 8, max = 20, message = "Description must be between 8 and 20 characters")
    private String descricao;


    public ProfileDto(Profile entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

