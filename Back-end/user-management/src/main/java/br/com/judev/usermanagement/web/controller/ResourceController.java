package br.com.judev.usermanagement.web.controller;

import br.com.judev.usermanagement.exception.ResourceNotFoundException;
import br.com.judev.usermanagement.service.ResourceService;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.response.ResourceResponseDto;
import br.com.judev.usermanagement.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource")
@CrossOrigin
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Operation(summary = "List of all registered Resources", description = "list all users that are on my list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered Resources",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResourceRequestDto.class)))),
                    @ApiResponse(responseCode = "401", description = "Resources without permission to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<ResourceResponseDto>> listAllResources() {
        List<ResourceResponseDto> resources = resourceService.listAll();
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @Operation(summary = "Create a new Resource", description ="Feature to create a new Resource",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceRequestDto.class))),
                    @ApiResponse(responseCode = "409", description = "Resource name already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/create")
    public ResponseEntity<ResourceResponseDto> createResource(@RequestBody ResourceRequestDto resourceRequestDto) {
        ResourceResponseDto createdResource = resourceService.create(resourceRequestDto);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }


    @Operation(summary = "update Name from  Resource", description ="Feature to update Resource Name ",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Name  updadet Sucessfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or poorly formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<ResourceResponseDto> updateResource(@PathVariable("id") Long resourceId, @RequestBody ResourceRequestDto resourceRequestDto) {
        try {
            ResourceResponseDto updatedResource = resourceService.updateName(resourceId, resourceRequestDto);
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Delete Resource",
            description = "Deletes a Resource by passing in a JSON, XML or YML representation of the Resource!",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable("id") Long resourceId) {
        try {
            resourceService.delete(resourceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Find a Resource ", description = "Find a Resource",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResourceRequestDto.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseDto> getResourceById(@PathVariable("id") Long resourceId) {
        try {
            ResourceResponseDto resource = resourceService.getUserById(resourceId);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
