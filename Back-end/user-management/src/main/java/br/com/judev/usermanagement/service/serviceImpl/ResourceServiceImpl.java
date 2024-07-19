package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.repository.ResourceRepository;
import br.com.judev.usermanagement.service.ResourceService;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.response.ResourceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    public ResourceResponseDto create(ResourceRequestDto resourceRequestDto) {
        return null;
    }

    @Override
    public List<ResourceResponseDto> listAll() {
        return List.of();
    }

    @Override
    public ResourceResponseDto update(Long resourceId, ResourceRequestDto resourceRequestDto) {
        return null;
    }

    @Override
    public void delete(Long userId) {

    }

    @Override
    public ResourceResponseDto getUserById(Long resourceId) {
        return null;
    }
}
