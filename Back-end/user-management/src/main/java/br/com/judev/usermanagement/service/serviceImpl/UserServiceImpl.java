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
import java.util.Optional;
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
    public RegisterUserResponseDto salvar(RegisterUserRequestDto userDto) {
        Optional<User> user = userRepository.findByEmailOrCpfCnpj(userDto.getEmail(), userDto.getCpfCnpj());
        if (user != null) {
            if (user.get().getEmail().equals(userDto.getEmail()))
                throw new EntityAlreadyExists("User with email " + userDto.getEmail() + " already exists.");

            if (user.get().getCpfCnpj().equals(userDto.getCpfCnpj()))
                throw new EntityAlreadyExists("User with CPF/CNPJ " + userDto.getCpfCnpj() + " already exists.");
        }
         
        User newUser = UserRegisterMapper.toUser(userDto);

          // Criptografa a senha na entidade antes de salvar
          String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
         
       /*   User newUser = new User();
          newUser.setEmail(userDto.getEmail());
          newUser.setPassword(encryptedPassword);
          newUser.setCpfCnpj(userDto.getCpfCnpj()); // Exemplo, se aplicável
          newUser.setRole(userDto.getRole());*/

     //     User newUser = new User(userDto.getEmail(), encryptedPassword, userDto.getRole(), userDto.getCpfCnpj());

      
        //newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setPassword(encryptedPassword);
        User savedUser = userRepository.save(newUser);

         // Gera um token JWT para o novo usuário registrado
        String token = this.tokenService.generateToken(newUser);
        // Envia email de boas-vindas
        emailService.sendWelcomeMessageToNewUser(savedUser.getEmail(), savedUser.getName());

        return UserRegisterMapper.ToDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + loginDto.getEmail() + " not found."));
        // Verifica se a senha está correta
       /*  if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordInvalidException("Invalid password for user with email " + loginDto.getEmail() + ".");
        }
        // Gera o token e retorna o DTO de resposta de login
        String token = tokenService.generateToken(user);*/

          // Cria o token de autenticação
          UsernamePasswordAuthenticationToken authToken = 
          new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

          // Autentica o usuário
          Authentication authentication = authenticationManager.authenticate(authToken);

         // Gera o token após autenticação bem-sucedida
          String token = tokenService.generateToken((User) authentication.getPrincipal());
  
            
        return new LoginResponseDto(token);
    }


    @Override
    public UserResponseDto update(Long userId, UserRequestDto userDto) {

        User entity = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));

        if (!entity.getCpfCnpj().equals(userDto.getCpfCnpj())) {
            throw new EntityNotFoundException("ENTITY NOT FOUND");
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
    public UserResponseDto updatePassword(Long userId, String currentPassword, String newPassword, String confirmPassword) {
        if(!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("New password does not match password confirmation.");
        }
        UserResponseDto user = getUserById(userId);
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
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));
        return UserMapper.ToDto(user);
    }
}
