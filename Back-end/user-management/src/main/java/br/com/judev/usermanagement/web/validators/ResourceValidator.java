package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.entity.Resource;
import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ResourceValidator {


    public void validateName(Resource resource, ResourceRequestDto resourceDto) {
        if (resource.getName().equals(resourceDto.getName())) {
            throw new IllegalArgumentException("Resource Name must be changed to update.");
        }
    }
    public  void validateKey(Resource resource, ResourceRequestDto resourceDto) {
        if (!resource.getResource_key().equals(resourceDto.getResource_Key())) {
            throw new IllegalArgumentException("Key can't be changed!");
        }
    }
}
