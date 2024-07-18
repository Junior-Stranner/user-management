package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.EntityAlreadyExists;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.repository.UserRepository;
import br.com.judev.usermanagement.service.UserService;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import br.com.judev.usermanagement.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponseDto> listAll() {
        List<User> users = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return UserMapper.toListDto(users);
    }


    @Override
    public UserResponseDto create(UserRequestDto userDto) {
        List<User> users = userRepository.findAll();
        boolean userExists = users.stream().anyMatch(uExists -> uExists.getEmail().equals(userDto.getEmail()));
        if (userExists) {
            throw new EntityAlreadyExists("User with email " + userDto.getEmail() + " already exists.");
        }
        User newUser = new User();
        User savedUser = userRepository.save(newUser);
        return UserMapper.ToDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));
        return UserMapper.ToDto(user);
    }

    @Override
    public UserResponseDto update(Long userId, UserRequestDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Verifica se o nome foi alterado
            if (!user.getName().equals(userDto.getName())) {
                user.setName(userDto.getName());

                User updatedUser = userRepository.save(user);
                return UserMapper.ToDto(updatedUser);
            } else {
                throw new IllegalArgumentException("Name must be changed to update.");
            }
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public UserResponseDto updatePassword(Long userId, String currentPassword, String newPassword, String confirmPassword) {
        return null;
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
