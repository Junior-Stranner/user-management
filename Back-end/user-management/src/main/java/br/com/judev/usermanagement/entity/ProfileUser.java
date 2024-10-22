/*package br.com.judev.usermanagement.entity;

import br.com.judev.usermanagement.web.dto.ProfileUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "tb_Profile_User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUser {

    @EmbeddedId
    private ProfileUserPK id;


    public ProfileUser(Profile profile, User user) {
        id.setProfile(profile);
        id.setUser(user);
    }

    public Profile getProfile() {
        return id.getProfile();
    }

    public void setProfile(Profile profile){
        id.setProfile(profile);
    }

    public User getUser() {
         return id.getUser();
    }

    public void setUser(User user) {
        this.id.setUser(user);
    }
}
*/