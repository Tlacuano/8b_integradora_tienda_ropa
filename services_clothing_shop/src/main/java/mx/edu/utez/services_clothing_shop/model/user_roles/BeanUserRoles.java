package mx.edu.utez.services_clothing_shop.model.user_roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
@EntityListeners(AuditEntityListener.class)
@Entity
public class BeanUserRoles {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user_role", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idUserRole;

    //relacion muchos a uno con la tabla users
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;

    //relacion muchos a uno con la tabla roles
    @ManyToOne
    @JoinColumn(name = "fk_id_role")
    private BeanRole role;
}
