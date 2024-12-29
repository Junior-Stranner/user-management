package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.exception.EntityAlreadyExists;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.exception.PasswordInvalidException;
import br.com.judev.usermanagement.exception.ResourceNotFoundException;
import br.com.judev.usermanagement.infra.security.TokenService;
import br.com.judev.usermanagement.repository.UserRepository;
import br.com.judev.usermanagement.service.EmailService;
import br.com.judev.usermanagement.service.UserService;
import br.com.judev.usermanagement.web.dto.UserChangePasswordDto;
import br.com.judev.usermanagement.web.dto.request.LoginRequestDto;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.LoginResponseDto;
import br.com.judev.usermanagement.web.dto.response.RegisterUserResponseDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import br.com.judev.usermanagement.web.mapper.UserMapper;
import br.com.judev.usermanagement.web.mapper.UserRegisterMapper;
import br.com.judev.usermanagement.web.validators.UserValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserValidator validator;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;


    @Override
    public List<UserResponseDto> listAll() {
        List<User> users = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return UserMapper.toListDto(users);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
       var user =  userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not fount !"));
        return UserMapper.ToDto(user);
    }

    @Override
    public RegisterUserResponseDto salvar(RegisterUserRequestDto userDto) {
        userRepository.findByEmailOrCpfCnpj(userDto.getEmail(), userDto.getCpfCnpj())
                .ifPresent(existingUser -> {
                    if (existingUser.getEmail().equals(userDto.getEmail())) {
                        throw new EntityAlreadyExists("User with email " + userDto.getEmail() + " already exists.");
                    }
                    if (existingUser.getCpfCnpj().equals(userDto.getCpfCnpj())) {
                        throw new EntityAlreadyExists("User with CPF/CNPJ " + userDto.getCpfCnpj() + " already exists.");
                    }
                });
         
        User newUser = UserRegisterMapper.toUser(userDto);

        // Criptografa a senha na entidade antes de salvar
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        newUser.setPassword(encryptedPassword);

        User savedUser = userRepository.save(newUser);

         // Gera um token JWT para o novo usuário registrado
        String token = this.tokenService.generateToken(newUser);

        // Envia email de boas-vindas
    //    emailService.sendWelcomeMessageToNewUser(savedUser.getEmail(), savedUser.getName());

        return UserRegisterMapper.ToDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {

        if ((loginDto.getEmail() == null || loginDto.getEmail().isBlank()) &&
                (loginDto.getCpfCnpj() == null || loginDto.getCpfCnpj().isBlank())) {
            throw new IllegalArgumentException("Either email or cpfCnpj must be provided.");
        }

        if (loginDto.getEmail() != null && !loginDto.getEmail().isBlank()) {
             userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("User with email " + loginDto.getEmail() + " not found."));
        } else {
             userRepository.findByCpfCnpj(loginDto.getCpfCnpj())
                    .orElseThrow(() -> new EntityNotFoundException("User with cpfCnpj " + loginDto.getCpfCnpj() + " not found."));
        }

        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        // Autentica o usuário e verifica as credenciais
        Authentication authentication = authenticationManager.authenticate(authToken);

        // Gera o token JWT para o usuário autenticado
        String token = tokenService.generateToken((User) authentication.getPrincipal());

        return new LoginResponseDto(token);
    }


    @Override
    public UserResponseDto update(Long userId, UserRequestDto userDto) {

        User entity = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));

        if (!entity.getCpfCnpj().equals(userDto.getCpfCnpj())) {
            throw new IllegalArgumentException("Invalid CPF/CNPJ for user with id: " + userId);
        }

        if (userDto.getName() != null) {
            validator.validateName(entity, userDto);
            entity.setName(userDto.getName());
        }

        validator.validateEmail(entity, userDto);
        validator.validatePassword(entity, userDto);

        User updatedUser = userRepository.save(entity);
        return UserMapper.ToDto(updatedUser);
    }

    @Override
    public UserChangePasswordDto updatePassword(Long userId, String currentPassword, String newPassword, String confirmPassword) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        // Verifica se a nova senha e a confirmação são iguais
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("New password does not match confirmation password.");
        }

        // Verifica se a nova senha é diferente da senha atual
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new PasswordInvalidException("New password must be different from the current password.");
        }

        // Valida se a senha atual está correta
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException("Current password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return new UserChangePasswordDto(currentPassword, newPassword, confirmPassword);
    }

    @Override
    public void delete(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));
        return UserMapper.ToDto(user);
    }
}
