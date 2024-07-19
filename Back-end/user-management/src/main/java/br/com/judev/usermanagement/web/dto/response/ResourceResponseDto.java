package br.com.judev.usermanagement.web.dto.response;

import lombok.Data;

@Data
public class ResourceResponseDto {
    private Long id;
    private String nome;
    private String chave;
}
