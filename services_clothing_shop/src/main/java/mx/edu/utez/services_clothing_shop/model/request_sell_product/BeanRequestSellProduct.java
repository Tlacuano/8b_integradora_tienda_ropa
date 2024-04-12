package mx.edu.utez.services_clothing_shop.model.request_sell_product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests_sell_product")
@EntityListeners(AuditEntityListener.class)
public class BeanRequestSellProduct {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_request_sell_product", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idRequestSellProduct;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    //relacion muchos a uno con la tabla de products
    @ManyToOne
    @JoinColumn(name = "fk_id_product")
    private BeanProduct product;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanRequestStatus status;
}
