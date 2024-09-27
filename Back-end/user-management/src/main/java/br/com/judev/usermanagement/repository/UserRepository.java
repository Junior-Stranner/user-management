package br.com.judev.usermanagement.repository;

import br.com.judev.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    Optional<User>findByEmail(String email);
    Optional<User> findByCpfCnpj(String cpfCnpj);

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.cpfCnpj = :cpfCnpj")
    Optional<User> findByEmailOrCpfCnpj(String email , String cpfCnpj);
}
