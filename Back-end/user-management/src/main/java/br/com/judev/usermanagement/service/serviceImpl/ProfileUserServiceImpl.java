package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.Profile;
import br.com.judev.usermanagement.entity.ProfileUser;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.exception.ProfileNotFoundException;
import br.com.judev.usermanagement.repository.ProfileRepository;
import br.com.judev.usermanagement.repository.ProfileUserRepository;
import br.com.judev.usermanagement.repository.UserRepository;
import br.com.judev.usermanagement.service.ProfileUserService;
import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import br.com.judev.usermanagement.web.mapper.ProfileUserMapper;
import br.com.judev.usermanagement.web.validators.ProfileUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProfileUserServiceImpl implements ProfileUserService {


    private final ProfileUserRepository profileUserRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileUserValidator validator;

    @Override
    public ProfileUserDto create(ProfileUserDto profileUserDto) {
        if (profileUserDto == null || profileUserDto == null) {
            throw new IllegalArgumentException("Profile ID and User ID must be provided");
        }

        Profile profile = profileRepository.findById(profileUserDto.getId())
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " ));

        User user = userRepository.findById(profileUserDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " ));

        ProfileUser profileUser = new ProfileUser();
        profileUser.setProfile(profile);
        profileUser.setUser(user);
        ProfileUser savedProfileUser = profileUserRepository.save(profileUser);

        return ProfileUserMapper.ToDto(savedProfileUser);
    }

    @Override
    public List<ProfileUserDto> listAll() {
        List<ProfileUser> profileUserList = StreamSupport
                .stream(profileUserRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return ProfileUserMapper.toListDto(profileUserList);
    }

    @Override
    public ProfileUserDto update(Long profileUserId, ProfileUserDto profileUserDto) {

        ProfileUser existingProfileUser = profileUserRepository.findById(profileUserId)
                .orElseThrow(() -> new EntityNotFoundException("ProfileUser not found with id: " + profileUserId));

      /*  if (profileUserDto.getProfile() != null && !profileUserDto.getProfile().getProfileId().equals(existingProfileUser.getProfile().getId())) {
            validator.validateProfile(profile, profileUserDto.getProfile());
            existingProfileUser.setProfile(profile);
        }

        if (profileUserDto.getUser() != null && !profileUserDto.getUser().getUserId().equals(existingProfileUser.getUser().getId())) {
            validator.ValiteUser(user, profileUserDto.getUser());
            existingProfileUser.setUser(user);
        }*/
        ProfileUser profileUser = new ProfileUser();
        profileUser.setProfile(profileUserDto.getProfile());
        profileUser.setUser(profileUserDto.getUser());
        ProfileUser updatedProfileUser = profileUserRepository.save(existingProfileUser);

        return ProfileUserMapper.ToDto(updatedProfileUser);
    }

    @Override
    public void delete(Long id) {
        if (profileUserRepository.existsById(id)) {
            profileUserRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("ProfileUser not found with id: " + id);
        }
    }

    @Override
    public ProfileUserDto getProfileUserById(Long id) {
        ProfileUser profileUser = profileUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfileUser not found with id: " + id));

        return ProfileUserMapper.ToDto(profileUser);
    }

}
