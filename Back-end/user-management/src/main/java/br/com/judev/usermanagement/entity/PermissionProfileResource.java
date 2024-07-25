package br.com.judev.usermanagement.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "tb_permission_profile_user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PermissionProfileResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile perfil;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id")
    private Resource recurso;


    /*public PermissionProfileResource(PermissionProfileResourceDto permissionProfileResourceDto) {
        BeanUtils.copyProperties(permissionProfileResource, this);
        if(permissionProfileResource != null && permissionProfileResource.getRecurso() != null) {
            this.resource = new Reource(permissaoPerfilRecurso.getRecurso());
        }
        if(permissionProfileResource != null && permissionProfileResource.getPerfil() != null) {
            this.profile = new profile(permissionProfileResource.getPerfil());
        }
    }*/

}
