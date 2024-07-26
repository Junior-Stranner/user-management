package br.com.judev.usermanagement.web.dto;

import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserDto {

    private Long id;
    private RegisterUserRequestDto user;
    private ProfileDto profile;

   /* public ProfileUserDto(ProfileUserService entity) {
        if (entity != null) {
            this.id = entity.getId();
            if (entity.getUser() != null) {
                this.user = new UserResponseDto(entity.getUser());
            }
            if (entity.getProfile() != null) {
                this.profile = new ProfileDto(entity.getProfile());
            }
        }*/
    }
