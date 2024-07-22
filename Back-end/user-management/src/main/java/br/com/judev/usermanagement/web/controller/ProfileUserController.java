package br.com.judev.usermanagement.web.controller;

import br.com.judev.usermanagement.exception.EntityAlreadyExists;
import br.com.judev.usermanagement.exception.EntityNotFoundException;
import br.com.judev.usermanagement.service.ProfileUserService;
import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile-user")
public class ProfileUserController {

    private final ProfileUserService profileUserService;

    @Autowired
    public ProfileUserController(ProfileUserService profileUserService) {
        this.profileUserService = profileUserService;
    }


    @Operation(summary = "Create a new Profile-User", description ="Feature to create a new Profile-User")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profile-User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileUserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @ExceptionHandler(EntityAlreadyExists.class)
    @PostMapping
    public ResponseEntity<ProfileUserDto> create(@RequestBody ProfileUserDto profileUserDto) {
        try {
            ProfileUserDto createdProfileUser = profileUserService.create(profileUserDto);
            return new ResponseEntity<>(createdProfileUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "List of all registered Profile-Users", description = "list all  Profile-Users that are on my list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered  Profile-Users",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProfileUserDto.class)))),
                    @ApiResponse(responseCode = "401", description = " Profile-Users without permission to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<ProfileUserDto>> listAll() {
        List<ProfileUserDto> profileUsers = profileUserService.listAll();
        return new ResponseEntity<>(profileUsers, HttpStatus.OK);
    }

    @Operation(summary = "Find a Profile-User ", description = "Profile-User",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileUserDto.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProfileUserDto> getById(@PathVariable Long id) {
        try {
            ProfileUserDto profileUserDto = profileUserService.getProfileUserById(id);
            return new ResponseEntity<>(profileUserDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "update a Profile-User", description ="Feature to update a Proifile-User",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Profile-User updadet Sucessfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileUserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Profile-User does not have a Authorization",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Profile-User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or poorly formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<ProfileUserDto> update(@PathVariable Long id, @RequestBody ProfileUserDto profileUserDto) {
        try {
            ProfileUserDto updatedProfileUser = profileUserService.update(id, profileUserDto);
            return new ResponseEntity<>(updatedProfileUser, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Delete Profile-User",
            description = "Deletes a Profile-User by passing in a JSON, XML or YML representation of the Profile-User!",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            profileUserService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
