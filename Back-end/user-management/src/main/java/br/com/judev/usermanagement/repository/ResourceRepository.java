package br.com.judev.usermanagement.repository;

import br.com.judev.usermanagement.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
