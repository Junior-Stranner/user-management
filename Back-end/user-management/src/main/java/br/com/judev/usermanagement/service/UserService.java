package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.dto.UserDto;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> listarTodos(){
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserDto::new).toList();
    }

    public UserDto inserir(UserDto userDto) {
        User userEntity = new User(userDto);
        User savedUser = userRepository.save(userEntity);
        return new UserDto(savedUser);
    }

    public UserDto alterar(UserDto userDto) {
        User user = new User(userDto);
        User updatedUser = userRepository.save(user);
        return new UserDto(updatedUser);
    }

    public void excluir(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }


}
