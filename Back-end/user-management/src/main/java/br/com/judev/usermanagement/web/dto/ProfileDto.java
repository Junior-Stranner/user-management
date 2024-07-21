package br.com.judev.usermanagement.web.dto;

import br.com.judev.usermanagement.entity.Profile;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ProfileDto {

    @NotBlank(message = "Profile ID cannot be blank")
    private Long profileId;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 8, max = 20, message = "Description must be between 8 and 20 characters")
    private String descricao;
    public ProfileDto(Profile entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

