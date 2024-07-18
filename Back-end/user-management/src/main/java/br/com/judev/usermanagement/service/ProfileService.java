package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {

    public ProfileDto getProfile();
    public ProfileDto saveProfile(Profile entity);
    public ProfileDto update(Profile profile);
    public void delete(Long profileId);
    public ProfileDto getProfileById(Long profileId);
}
