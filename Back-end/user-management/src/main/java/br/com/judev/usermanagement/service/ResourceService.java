package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.request.UserRequestDto;
import br.com.judev.usermanagement.web.dto.response.ResourceResponseDto;
import br.com.judev.usermanagement.web.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceService {

    public ResourceResponseDto create(ResourceRequestDto resourceRequestDto);
    public List<ResourceResponseDto> listAll();
    public ResourceResponseDto update(Long resourceId, ResourceRequestDto resourceRequestDto);
    public void delete(Long userId);
    public ResourceResponseDto getUserById(Long resourceId);
}
