package br.com.judev.usermanagement.web.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
public class ResourceResponseDto {
    private Long resourceId;
    private String name;
    private String resource_Key;
}
