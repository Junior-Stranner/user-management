package br.com.judev.usermanagement.web.mapper;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.request.RegisterUserRequestDto;
import br.com.judev.usermanagement.web.dto.response.RegisterUserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static User toUser(RegisterUserRequestDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public static RegisterUserResponseDto ToDto(User entity){
        return modelMapper.map(entity,RegisterUserResponseDto.class);
    }


    public static List<RegisterUserResponseDto> toListDto(List<User> users) {
        return users.stream().map(user -> ToDto(user)).collect(Collectors.toList());
    }

}
