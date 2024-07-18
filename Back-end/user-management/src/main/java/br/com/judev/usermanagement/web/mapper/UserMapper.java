package br.com.judev.usermanagement.web.mapper;

import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserResponseDto dto){
        return new ModelMapper().map(dto,User.class);
    }

    public static UserResponseDto ToDto(User entity){
        return new ModelMapper().map(entity,UserResponseDto.class);
    }


    public static List<UserResponseDto> toListDto(List<User> users) {
        return users.stream().map(user -> ToDto(user)).collect(Collectors.toList());
    }

}
