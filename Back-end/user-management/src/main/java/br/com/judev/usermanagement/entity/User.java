package br.com.judev.usermanagement.entity;

import br.com.judev.usermanagement.web.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Entity
@Table(name ="tb_user")
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false , length = 100)
    private String password;

    @Column(nullable = false)
    private String email;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }

    public User(UserDto dto){
        BeanUtils.copyProperties(dto, this);
    }


}
