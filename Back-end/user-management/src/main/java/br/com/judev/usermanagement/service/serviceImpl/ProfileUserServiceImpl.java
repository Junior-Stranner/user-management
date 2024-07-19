package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.repository.ProfileRepository;
import br.com.judev.usermanagement.service.ProfileService;
import br.com.judev.usermanagement.service.ProfileUserService;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileUserServiceImpl implements ProfileUserService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileUserDto create(UserRequestDto userDto) {
        return null;
    }

    @Override
    public List<ProfileUserDto> listAll() {
        return List.of();
    }

    @Override
    public ProfileUserDto update(Long userId, UserRequestDto userDto) {
        return null;
    }

    @Override
    public void delete(Long userId) {

    }

    @Override
    public ProfileUserDto getProfileUserById(Long userId) {
        return null;
    }
}
