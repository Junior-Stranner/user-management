package br.com.judev.usermanagement.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Objects;

@Data
public class ResourceRequestDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @NotBlank(message = "Key cannot be blank")
    @Size(min = 6, max = 20, message = "Key must be between 6 and 20 characters")
    private String resource_Key;

}
