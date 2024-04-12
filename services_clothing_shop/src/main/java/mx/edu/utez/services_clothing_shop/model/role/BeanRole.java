package mx.edu.utez.services_clothing_shop.model.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
@EntityListeners(AuditEntityListener.class)
public class BeanRole {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_role", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idRole;

    @Column(name = "role_name", length = 50)
    private String roleName;


    //relacion muchos a muchos con la tabla users
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<BeanUserRoles> users;
}
