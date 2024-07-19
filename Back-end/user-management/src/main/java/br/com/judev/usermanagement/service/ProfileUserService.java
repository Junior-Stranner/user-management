package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfileUserService {
    public ProfileUserDto create(UserRequestDto userDto);
    public List<ProfileUserDto> listAll();
    public ProfileUserDto update(Long userId, UserRequestDto userDto);
    public void delete(Long id);
    public ProfileUserDto getProfileUserById(Long id);

}
