package br.com.judev.usermanagement.web.controller;

import br.com.judev.usermanagement.exception.EmailUniqueViolationException;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.exception.NameNotChangeException;
import br.com.judev.usermanagement.service.UserService;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController implements UserDocumentation{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserResponseDto> listAllUsers() {
        return userService.listAll();
    }

    @Operation(summary = "Retrieve user by email", description = "Fetches user details based on the provided email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @ExceptionHandler(EmailUniqueViolationException.class)
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        var userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler(NameNotChangeException.class)
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public UserResponseDto updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userDto) {
        return userService.update(userId, userDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public UserResponseDto getUserByCpfOrCnpj(@PathVariable String cpfCnpj) {
        return userService.getUserByCpfOrCnpj(cpfCnpj);
    }
}
