package br.com.judev.usermanagement.web.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
public class RegisterUserResponseDto  {
    private String token;

}
