package br.com.judev.usermanagement.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Objects;

@Data
public class ResourceRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    @Size(min = 6 , max = 20)
    private String chave;

}
