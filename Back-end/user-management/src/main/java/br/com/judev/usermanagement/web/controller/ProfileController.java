package br.com.judev.usermanagement.web.controller;

import br.com.judev.usermanagement.service.ProfileService;
import br.com.judev.usermanagement.web.dto.ProfileDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@ControllerAdvice
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Operation(summary = "List of all registered Profile", description = "list all Profiles that are on my list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered Profiles",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserRequestDto.class)))),
                    @ApiResponse(responseCode = "401", description = "User without permission to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public List<ProfileDto> getAllProfiles() {
        return profileService.getProfile();
    }

    @Operation(summary = "Find a Profile ", description = "Find a Profile",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/{profileId}")
    public ProfileDto getProfileById(@PathVariable Long profileId) {
        return profileService.getProfileById(profileId);
    }


    @Operation(summary = "Create a new Profile", description ="Feature to create a new Profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDto.class))),
                    @ApiResponse(responseCode = "409", description = "User description profile already exists on system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ProfileDto createProfile(@RequestBody ProfileDto profileDto) {
        return profileService.saveProfile(profileDto);
    }


    @Operation(summary = "Create a new Profile", description ="Feature to update a Profile",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Profile updadet Sucessfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDto.class))),
                    @ApiResponse(responseCode = "400", description = " description does not match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Profile does not have a Authorization",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Profile not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or poorly formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{profileId}")
    public ProfileDto updateProfile(@PathVariable Long profileId, @RequestBody ProfileDto profileDto) {
        profileService.getProfileById(profileId);
        return profileService.update(profileDto);
    }

    @Operation(summary = "Delete Profile",
            description = "Deletes a Profile by passing in a JSON, XML or YML representation of the Profile!",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    @DeleteMapping("/{profileId}")
    public void deleteProfile(@PathVariable Long profileId) {
        profileService.delete(profileId);
    }

}
