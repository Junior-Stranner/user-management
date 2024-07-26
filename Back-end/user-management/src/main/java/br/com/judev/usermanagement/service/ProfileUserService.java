package br.com.judev.usermanagement.service;

import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfileUserService {
    public ProfileUserDto create(ProfileUserDto profileUserDto);
    public List<ProfileUserDto> listAll();
    public ProfileUserDto update(Long profileUserId, ProfileUserDto profileUserDto);
    public void delete(Long id);
    public ProfileUserDto getProfileUserById(Long id);

}
