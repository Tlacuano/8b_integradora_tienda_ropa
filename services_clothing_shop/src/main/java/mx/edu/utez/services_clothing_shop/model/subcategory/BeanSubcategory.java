package mx.edu.utez.services_clothing_shop.model.subcategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subcategories")
public class BeanSubcategory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_subcategory", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_subcategory;

    //relacion muchos a uno con la tabla categories
    @ManyToOne
    @JoinColumn(name = "fk_id_category")
    private BeanCategory category;

    //relacion muchos a uno con la tabla status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion uno a muchos con la tabla de products
    @OneToMany(mappedBy = "subcategory")
    private List<BeanProduct> products;
}
