package br.com.judev.usermanagement.web.mapper;

import br.com.judev.usermanagement.entity.ProfileUser;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileUserMapper {

    public static ProfileUser toProfileUser(ProfileUserDto dto){
        return new ModelMapper().map(dto,ProfileUser.class);
    }

    public static ProfileUserDto ToDto(ProfileUser entity){
        return new ModelMapper().map(entity,ProfileUserDto.class);
    }


    public static List<ProfileUserDto> toListDto(List<ProfileUser> entitys) {
        return entitys.stream().map(profileUser -> ToDto(profileUser)).collect(Collectors.toList());
    }

}
