package mx.edu.utez.services_clothing_shop.model.product_gallery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product_gallery")
@EntityListeners(AuditEntityListener.class)
public class BeanProductGallery {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_image", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idImage;

    @Column(name = "image", length = 255)
    private String image;

    //relacion muchos a uno con la tabla de products
    @ManyToOne
    @JoinColumn(name = "fk_id_product")
    @JsonIgnore
    private BeanProduct product;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanImageProductStatus status;
}
