package br.com.judev.usermanagement.web.dto;

import br.com.judev.usermanagement.entity.Profile;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ProfileDto {

    private Long id;
    private String descricao;

    public ProfileDto(Profile entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

