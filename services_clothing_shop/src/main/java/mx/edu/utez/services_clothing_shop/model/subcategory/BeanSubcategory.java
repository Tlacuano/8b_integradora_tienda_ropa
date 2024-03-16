package mx.edu.utez.services_clothing_shop.model.subcategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
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
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_subcategory", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idSubcategory;

    @Column(name = "subcategory", length = 30)
    private String subcategory;

    @Column(name = "image")
    private String image;

    //relacion muchos a uno con la tabla categories
    @ManyToOne
    @JoinColumn(name = "fk_id_category")
    private BeanCategory category;

    @Column(name = "status", columnDefinition = "TINYINT(1)")
    private boolean status;

    //relacion uno a muchos con la tabla de products
    @OneToMany(mappedBy = "subcategory")
    @JsonIgnore
    private List<BeanProduct> products;
}
