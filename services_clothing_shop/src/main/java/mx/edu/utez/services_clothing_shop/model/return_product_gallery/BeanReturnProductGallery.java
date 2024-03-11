package mx.edu.utez.services_clothing_shop.model.return_product_gallery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "return_product_gallery")
public class BeanReturnProductGallery {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_image", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idImage;

    @Column(name = "image", length = 100)
    private String image;

    //relacion muchos a uno con la tabla de return products
    @ManyToOne
    @JoinColumn(name = "fk_id_request_return_product")
    @JsonIgnore
    private BeanRequestReturnProduct returnProduct;
}
