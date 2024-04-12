package mx.edu.utez.services_clothing_shop.model.request_become_seller;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests_become_seller")
@EntityListeners(AuditEntityListener.class)
public class BeanRequestsBecomeSeller {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_request_become_seller", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idRequestBecomeSeller;

    @Column(name = "user_seller_information", columnDefinition = "JSON")
    private String userSellerInformation;

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
