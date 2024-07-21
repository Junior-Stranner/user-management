package br.com.judev.usermanagement.service.serviceImpl;

import br.com.judev.usermanagement.entity.Resource;
import br.com.judev.usermanagement.exception.ResourceNotFoundException;
import br.com.judev.usermanagement.repository.ResourceRepository;
import br.com.judev.usermanagement.service.ResourceService;
import br.com.judev.usermanagement.web.dto.request.ResourceRequestDto;
import br.com.judev.usermanagement.web.dto.response.ResourceResponseDto;
import br.com.judev.usermanagement.web.mapper.ResourceMapper;
import br.com.judev.usermanagement.web.validators.ResourceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceValidator validator;

    private final ResourceRepository resourceRepository;

    @Override
    public List<ResourceResponseDto> listAll() {
        List<Resource> rescources = StreamSupport
                .stream(resourceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return ResourceMapper.toListDto(rescources);
    }

    @Override
    public ResourceResponseDto create(ResourceRequestDto resourceRequestDto) {
        Resource newResource = new Resource();
        Resource savedResource = resourceRepository.save(newResource);
        return ResourceMapper.toDto(savedResource) ;
    }


    @Override
    public ResourceResponseDto update(Long resourceId, ResourceRequestDto resourceRequestDto) {
         Optional<Resource> optionalResource = resourceRepository.findById(resourceId);
             if(optionalResource.isPresent()){
             Resource resource = optionalResource.get();

             validator.validateName(resource, resourceRequestDto);
             validator.validateKey(resource,resourceRequestDto);

               resource.setName(resource.getName());

               Resource updatedResource = resourceRepository.save(resource);
               return ResourceMapper.toDto(updatedResource);
         }else{
             throw new ResourceNotFoundException("Resource not found with id: " + resourceId);
         }
    }

    @Override
    public void delete(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                () -> new ResourceNotFoundException("resource not found with id: " + resourceId));
        resourceRepository.delete(resource);
    }

    @Override
    public ResourceResponseDto getUserById(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                () -> new ResourceNotFoundException("resource not found with id: " + resourceId));
        return ResourceMapper.toDto(resource);
    }
}
