package br.com.judev.usermanagement.repository;

import br.com.judev.usermanagement.entity.ProfileUser;
import br.com.judev.usermanagement.entity.ProfileUserPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileUserRepository extends JpaRepository<ProfileUser, Long> {
}
