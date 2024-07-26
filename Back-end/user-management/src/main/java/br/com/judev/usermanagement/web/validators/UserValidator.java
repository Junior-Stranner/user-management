package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import org.springframework.stereotype.Component;

@Component // Anotação para que o Spring possa gerenciar o bean
public class UserValidator {



    public  void validatePassword(User user, RegisterUserRequestDto userDto) {
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new IllegalArgumentException("Password can't be changed!");
        }
    }

    public void validateName(User user, RegisterUserRequestDto userDto) {
        if (user.getName().equals(userDto.getName())) {
            throw new IllegalArgumentException("Name must be changed to update.");
        }
    }

    public  void validateEmail(User user, RegisterUserRequestDto userDto) {
        if (!user.getEmail().equals(userDto.getEmail())) {
            throw new IllegalArgumentException("Email can't be changed!");
        }
    }

    public  void validateLogin(User user, RegisterUserRequestDto userDto) {
        if (!user.getLogin().equals(userDto.getLogin())) {
            throw new IllegalArgumentException("Login can't be changed!");
        }
    }
}
