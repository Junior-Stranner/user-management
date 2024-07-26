package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProfileUserValidator {

    public void ValiteUser(User user, RegisterUserRequestDto userRequestDto) {
    if (user.getId().equals(userRequestDto.getUserId())) {
        throw new IllegalArgumentException("User Id  must be changed to update.");
    }
}
    public  void validateProfile(Profile profile, ProfileDto dto) {
        if (profile.getId().equals(dto.getProfileId())) {
            throw new IllegalArgumentException("Profile Id must be changed to Update.");
        }
    }
}
