package br.com.judev.usermanagement.web.mapper;

import br.com.judev.usermanagement.entity.Resource;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.response.ResourceResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceMapper {

    public static Resource toResource(ResourceResponseDto dto){
     return new ModelMapper().map(dto, Resource.class);
    }

    public static ResourceResponseDto toDto(Resource entity){
        return new ModelMapper().map(entity, ResourceResponseDto.class);
    }

    public static List<ResourceResponseDto> toListDto(List<Resource> entitys){
        return entitys.stream().map(dto -> toDto(dto)).collect(Collectors.toList());
    }
}
