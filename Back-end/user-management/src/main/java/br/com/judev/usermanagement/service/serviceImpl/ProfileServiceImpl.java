package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.entity.Resource;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.exception.ProfileNotFoundException;
import br.com.judev.usermanagement.repository.ProfileRepository;
import br.com.judev.usermanagement.service.ProfileService;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import br.com.judev.usermanagement.web.validators.ProfileValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private  final ProfileValidator validator;

    private final ProfileRepository profileRepository;

    @Override
    public List<ProfileDto> getProfile() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileDto::new).collect(Collectors.toList());
    }

    @Override
    public ProfileDto saveProfile(ProfileDto dto) {
        Profile profile = new Profile();
        profile.setDescricao(dto.getDescricao());
        Profile saveProfile = profileRepository.save(profile);
        return new ProfileDto(saveProfile);
    }

    @Override
    public ProfileDto update(ProfileDto dto, Long profileId) {
        Optional<Profile> optionalResource = profileRepository.findById(profileId);
        if (optionalResource.isPresent()) {
            Profile profile = optionalResource.get();

            validator.validatedescripton(profile, dto);

            profile.setDescricao(dto.getDescricao());
            Profile updateProfile = profileRepository.save(profile);

            return new ProfileDto(updateProfile);
        }else{
            throw new EntityNotFoundException("Profile not found with id : "+profileId);
        }
    }

    @Override
    public void delete(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new ProfileNotFoundException("Profile not found with id : "+profileId));
      profileRepository.delete(profile);
    }

    @Override
    public ProfileDto getProfileById(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new ProfileNotFoundException("Profile not found with id : "+profileId));
        return new ProfileDto(profile);
    }
}
