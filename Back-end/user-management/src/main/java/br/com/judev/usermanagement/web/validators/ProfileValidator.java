package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.entity.Resource;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator {


    public void validatedescripton(Profile profile, ProfileDto dto) {
        if (profile.getDescricao().equals(dto.getDescricao())) {
            throw new IllegalArgumentException("Description  must be changed to update.");
        }
    }
}
