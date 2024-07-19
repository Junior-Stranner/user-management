package br.com.judev.usermanagement.service;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public UserResponseDto create(UserRequestDto userDto);
    public List<UserResponseDto> listAll();
    public UserResponseDto update(Long userId, UserRequestDto userDto);
    public UserResponseDto updatePassword(Long userId, String currentPassword, String newPassword, String confirmPassword);
    public void delete(Long userId);
    public UserResponseDto getUserById(Long userId);

}
