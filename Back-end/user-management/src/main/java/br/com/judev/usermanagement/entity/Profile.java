package br.com.judev.usermanagement.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "tb_Profile")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String descricao;

    public Profile(Profile profileDto) {
        BeanUtils.copyProperties(profileDto, this);
    }


}
