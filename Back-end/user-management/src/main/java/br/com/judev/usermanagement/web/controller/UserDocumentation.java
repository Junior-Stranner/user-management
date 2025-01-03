package br.com.judev.usermanagement.web.controller;

import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import br.com.judev.usermanagement.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface UserDocumentation {

    @Operation(summary = "List of all registered users", description = "list all users that are on my list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered users",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RegisterUserRequestDto.class)))),
                    @ApiResponse(responseCode = "401", description = "User without permission to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public List<UserResponseDto> listAllUsers();


    @Operation(summary = "update a new user", description ="Feature to update a user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User updadet Sucessfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterUserRequestDto.class))),
                    @ApiResponse(responseCode = "400", description = " Password does not match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "User does not have a Authorization",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or poorly formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public UserResponseDto updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userDto);


    @Operation(summary = "Delete User",
            description = "Deletes a User by passing in a JSON, XML or YML representation of the person!",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public void deleteUser(@PathVariable Long userId);


    @Operation(summary = "Find a User by Cpf or Cnpj ", description = "Find a Cpf or Cnpj specificly",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = RegisterUserRequestDto.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public UserResponseDto getUserByCpfOrCnpj(@PathVariable String cpfCnpj);
}
