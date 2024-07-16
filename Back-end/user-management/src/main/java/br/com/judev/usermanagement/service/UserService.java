package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.web.dto.UserDto;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> listAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserDto::new).toList();
    }

    public UserDto create(UserDto userDto) {
        User newUser = new User(userDto);
        User savedUser = userRepository.save(newUser);
        return new UserDto(savedUser);
    }

    public UserDto update(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
         //   user.setName(userDto.getName());
            User updatedUser = userRepository.save(user);
            return new UserDto(updatedUser);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userDto.getId());
        }
    }


    public UserDto updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        return null;
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

}
