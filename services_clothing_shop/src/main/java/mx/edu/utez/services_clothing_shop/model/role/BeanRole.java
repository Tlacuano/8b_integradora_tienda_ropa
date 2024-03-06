package mx.edu.utez.services_clothing_shop.model.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class BeanRole {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_role", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idRole;

    @Column(name = "role_name", length = 50)
    private String roleName;

    //relacion muchos a muchos con la tabla users
    @OneToMany(mappedBy = "role")
    private List<BeanUserRoles> users;
}
