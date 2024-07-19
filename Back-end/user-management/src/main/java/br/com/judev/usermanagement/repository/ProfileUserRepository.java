package br.com.judev.usermanagement.repository;

import br.com.judev.usermanagement.entity.ProfileUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileUserRepository extends JpaRepository<ProfileUser, Long> {
}
