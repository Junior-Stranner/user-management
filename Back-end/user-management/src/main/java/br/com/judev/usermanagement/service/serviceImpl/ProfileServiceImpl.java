package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.service.ProfileService;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final Profile;

    @Override
    public ProfileDto getProfile() {
        List<Profile> profiles = perfilRepository.findAll();
        return profiles.stream().map(ProfileDto::new).toList();
    }

    @Override
    public ProfileDto saveProfile(Profile entity) {
        return null;
    }

    @Override
    public ProfileDto update(Profile profile) {
        return null;
    }

    @Override
    public void delete(Long profileId) {

    }

    @Override
    public ProfileDto getProfileById(Long profileId) {
        return null;
    }
}
