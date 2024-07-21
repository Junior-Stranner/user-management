package br.com.judev.usermanagement.web.dto.response;

import lombok.Data;

@Data
public class ResourceResponseDto {
    private Long resourceId;
    private String name;
    private String key;
}
