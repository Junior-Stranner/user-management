package br.com.judev.usermanagement.web.controller;

import br.com.judev.usermanagement.exception.EntityAlreadyExists;
import br.com.judev.usermanagement.service.UserService;
import br.com.judev.usermanagement.web.dto.PasswordRecoveryDto;
import br.com.judev.usermanagement.web.dto.request.LoginRequestDto;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import br.com.judev.usermanagement.web.dto.response.LoginResponseDto;
import br.com.judev.usermanagement.web.dto.response.RegisterUserResponseDto;
import br.com.judev.usermanagement.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @Operation(summary = "Create a new user", description ="Feature to create a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User creadet in successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterUserRequestDto.class))),
                    @ApiResponse(responseCode = "409", description = "User e-mail already exists on system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @ExceptionHandler(EntityAlreadyExists.class)
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody @Valid RegisterUserRequestDto userDto) {
        RegisterUserResponseDto response = userService.register(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Login a  user", description ="Feature to login user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User logged in successfully\n",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterUserRequestDto.class))),
                    @ApiResponse(responseCode = "409", description = "User e-mail already exists on system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
        LoginResponseDto response = userService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
