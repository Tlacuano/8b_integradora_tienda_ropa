package mx.edu.utez.services_clothing_shop.model.request_return_product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests_return_product")
@EntityListeners(AuditEntityListener.class)
public class BeanRequestReturnProduct {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_request_return_product", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idRequestReturnProduct;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    @Column(name = "return_reason", length = 255)
    private String returnReason;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanRequestStatus status;

    //relacion muchos a uno con la tabla de orders has products
    @ManyToOne
    @JoinColumn(name = "order_has_product")
    private BeanOrderHasProducts orderHasProduct;

    //relacion uno a muchos con la tabla de return product gallery
    @OneToMany(mappedBy = "returnProduct")
    private List<BeanReturnProductGallery> returnProductGallery;


}
