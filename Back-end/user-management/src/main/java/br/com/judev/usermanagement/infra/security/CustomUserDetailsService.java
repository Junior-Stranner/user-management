package br.com.judev.usermanagement.infra.security;


import br.com.judev.usermanagement.entity.User;
import br.com.judev.usermanagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

 /*   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());

         //authorities // Aqui você passa os roles como GrantedAuthority
        //new ArrayList<>() representa que o usuário não tem permissões associadas.
    }*/

 /*   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo email ou CPF/CNPJ
        User user = userRepository.findByEmailOrCpfCnpj(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        // Criação do GrantedAuthority baseado no role do usuário
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // ou qualquer outro campo que você queira utilizar como identificador
                user.getPassword(),
                List.of(authority)
        );
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo email (username)
        User user = userRepository.findByEmailOrCpfCnpj(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        // Retorna uma instância do UserDetails com as informações do usuário
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),          // username (email)
                user.getPassword(),       // senha
                new ArrayList<>()         // Autoridades (roles ou permissões)
        );
    }


}
