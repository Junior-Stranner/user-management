package br.com.judev.usermanagement.entity;

import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "tb_Profile_User")
@Getter
@Setter
@NoArgsConstructor
public class ProfileUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;


  /*  // Construtor que aceita um ProfileUserDto
    public ProfileUserService(ProfileUserDto dto) {
        if (dto != null) {
            this.id = dto.getId(); // Copiar o ID se necessário
            if (dto.getUser() != null) {
                this.user = new User(dto.getUser()); // Assumindo que User tem um construtor que aceita UserResponseDto
            }
            if (dto.getProfile() != null) {
                this.profile = new Profile(dto.profile()); // Assumindo que Profile tem um construtor que aceita ProfileDto
            }
        }*/
    }
