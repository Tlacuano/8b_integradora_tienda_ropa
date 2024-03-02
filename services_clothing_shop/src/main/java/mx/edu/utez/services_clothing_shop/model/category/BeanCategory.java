package mx.edu.utez.services_clothing_shop.model.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "categories")
@Entity
public class BeanCategory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_category", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_category;

    @Column(name = "category", length = 60)
    private String category;

    @Column(name = "image", length = 100)
    private String image;

    //relacion muchos a uno con la tabla status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion uno a muchos con la tabla subcategories
    @OneToMany(mappedBy = "category")
    private List<BeanSubcategory> subcategories;
}
