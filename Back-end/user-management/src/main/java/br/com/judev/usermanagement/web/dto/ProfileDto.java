package br.com.judev.usermanagement.web.dto;

import br.com.judev.usermanagement.entity.Profile;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ProfileDto {

    private Long id;
    @NotBlank
    @Size(min = 8 , max = 20)
    private String descricao;

    public ProfileDto(Profile entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

