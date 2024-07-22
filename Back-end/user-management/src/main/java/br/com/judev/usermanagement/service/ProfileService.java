package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfileService {

    public List<ProfileDto> getProfile();
    public ProfileDto saveProfile(ProfileDto dto);
    public ProfileDto updateDesc(ProfileDto dto, Long profileId);
    public void delete(Long profileId);
    public ProfileDto getProfileById(Long profileId);
}
