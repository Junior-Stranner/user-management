package br.com.judev.usermanagement.repository;

import br.com.judev.usermanagement.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile , Long> {
}
