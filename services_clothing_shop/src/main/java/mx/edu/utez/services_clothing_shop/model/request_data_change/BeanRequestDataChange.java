package mx.edu.utez.services_clothing_shop.model.request_data_change;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests_data_change")
public class BeanRequestDataChange {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_request_data_change", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_request_data_change;

    @Column(name = "new_user_information", columnDefinition = "JSON")
    private String new_user_information;

    @Column(name="rejection_reason", length = 255)
    private String rejection_reason;

    //relacion muchos a uno con la tabla de users
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;
}
