package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.entity.Resource;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import org.springframework.stereotype.Component;

@Component // Anotação para que o Spring possa gerenciar o bean
public class UserValidator {



    public  void validatePassword(User user, UserRequestDto userDto) {
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new IllegalArgumentException("Password can't be changed!");
        }
    }

    public void validateName(User user, UserRequestDto userDto) {
        if (user.getName().equals(userDto.getName())) {
            throw new IllegalArgumentException("Name must be changed to update.");
        }
    }

    public  void validateEmail(User user, UserRequestDto userDto) {
        if (!user.getEmail().equals(userDto.getEmail())) {
            throw new IllegalArgumentException("Email can't be changed!");
        }
    }

    public  void validateLogin(User user, UserRequestDto userDto) {
        if (!user.getLogin().equals(userDto.getLogin())) {
            throw new IllegalArgumentException("Login can't be changed!");
        }
    }
}
