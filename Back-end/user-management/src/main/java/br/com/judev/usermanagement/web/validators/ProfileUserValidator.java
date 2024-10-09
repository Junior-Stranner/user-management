package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProfileUserValidator {

   /* public void validateUser(User user, UserRequestDto userRequestDto) {
        if (userRequestDto.getUserId() != null) { // Certifique-se de que não está enviando um ID.
            throw new IllegalArgumentException("User Id must not be provided for updates.");
        }
    }

    public void validateProfile(Profile profile, ProfileDto dto) {
        if (dto.getProfileId() != null) { // Certifique-se de que não está enviando um ID.
            throw new IllegalArgumentException("Profile Id must not be provided for updates.");
        }
    }*/

}
