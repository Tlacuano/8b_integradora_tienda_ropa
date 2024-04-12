package mx.edu.utez.services_clothing_shop.model.image_product_status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "image_product_status")
@EntityListeners(AuditEntityListener.class)
public class BeanImageProductStatus {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_status", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idStatus;

    @Column(name = "status", length = 50)
    private String status;

    //relacion uno a muchos con la tabla de producct gallery
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanProductGallery> productGallery;
}
