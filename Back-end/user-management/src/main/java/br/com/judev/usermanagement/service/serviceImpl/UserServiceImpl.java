package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.EntityAlreadyExists;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.exception.PasswordInvalidException;
import br.com.judev.usermanagement.infra.security.TokenService;
import br.com.judev.usermanagement.repository.UserRepository;
import br.com.judev.usermanagement.service.EmailService;
import br.com.judev.usermanagement.service.UserService;
import br.com.judev.usermanagement.web.dto.request.LoginRequestDto;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import br.com.judev.usermanagement.web.dto.response.LoginResponseDto;
import br.com.judev.usermanagement.web.dto.response.RegisterUserResponseDto;
import br.com.judev.usermanagement.web.mapper.UserMapper;
import br.com.judev.usermanagement.web.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public List<RegisterUserResponseDto> listAll() {
        List<User> users = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return UserMapper.toListDto(users);
    }


    @Override
    public RegisterUserResponseDto register(RegisterUserRequestDto userDto) {
        Optional<User> user = userRepository.findByEmailOrCpfCnpj(userDto.getEmail(), userDto.getCpfCnpj());
        if (user.isPresent()) {
            if (user.get().getEmail().equals(userDto.getEmail()))
                throw new EntityAlreadyExists("User with email " + userDto.getEmail() + " already exists.");

            if (user.get().getCpfCnpj().equals(userDto.getCpfCnpj()))
                throw new EntityAlreadyExists("User with CPF/CNPJ " + userDto.getCpfCnpj() + " already exists.");
        }

        User newUser = UserMapper.toUser(userDto);
        // Criptografa a senha na entidade antes de salvar
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(newUser);

        emailService.sendWelcomeMessageToNewUser(savedUser.getEmail(), savedUser.getName());

        return UserMapper.ToDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + loginDto.getEmail() + " not found."));
        // Verifica se a senha está correta
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordInvalidException("Invalid password for user with email " + loginDto.getEmail() + ".");
        }
        // Gera o token e retorna o DTO de resposta de login
        String token = tokenService.generateToken(user);
        return new LoginResponseDto(user.getId(), token);
    }


    @Override
    public RegisterUserResponseDto update(Long userId, RegisterUserRequestDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (userDto.getName() != null) {
                validator.validateName(user, userDto);
                user.setName(userDto.getName());
            }
            validator.validateEmail(user, userDto);
            validator.validateLogin(user, userDto);
            validator.validatePassword(user, userDto);

            user.setName(userDto.getName());

            User updatedUser = userRepository.save(user);
            return UserMapper.ToDto(updatedUser);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public RegisterUserResponseDto updatePassword(Long userId, String currentPassword, String newPassword, String confirmPassword) {
        if(!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("New password does not match password confirmation.");
        }
        RegisterUserResponseDto user = getUserById(userId);
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new PasswordInvalidException("Your password does not match.");
        }
        user.setPassword(passwordEncoder.encode(newPassword)); // Atualiza a senha do usuário
        return user;
    }

    @Override
    public void delete(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }


    @Override
    public RegisterUserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));
        return UserMapper.ToDto(user);
    }
}
