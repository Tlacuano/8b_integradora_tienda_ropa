package mx.edu.utez.services_clothing_shop.model.request_return_product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.oder_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests_return_product")
public class BeanRequestReturnProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_request_return_product", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_request_return_product;

    @Column(name ="reject_reason", length = 255)
    private String reject_reason;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion muchos a uno con la tabla de orders has products
    @ManyToOne
    @JoinColumn(name = "fk_id_order_product")
    private BeanOrderHasProducts order_has_product;

    //relacion uno a muchos con la tabla de return product gallery
    @OneToMany(mappedBy = "return_product")
    private List<BeanReturnProductGallery> return_product_gallery;


}
