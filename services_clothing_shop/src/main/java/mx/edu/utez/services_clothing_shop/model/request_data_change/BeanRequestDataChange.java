package mx.edu.utez.services_clothing_shop.model.request_data_change;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests_data_change")
@EntityListeners(AuditEntityListener.class)
public class BeanRequestDataChange {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_request_data_change", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idRequestDataChange;

    @Column(name = "new_user_information", columnDefinition = "JSON")
    private String newUserInformation;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    //relacion muchos a uno con la tabla de users
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanRequestStatus status;
}
