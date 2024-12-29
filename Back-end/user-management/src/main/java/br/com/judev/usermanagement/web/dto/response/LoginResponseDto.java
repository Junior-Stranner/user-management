package br.com.judev.usermanagement.web.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {
    String token;

    public LoginResponseDto(String token) {
    }
}
