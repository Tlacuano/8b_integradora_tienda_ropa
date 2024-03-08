package mx.edu.utez.services_clothing_shop.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_category", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idCategory;

    @Column(name = "category", length = 60)
    private String category;

    @Column(name = "image", length = 100)
    private String image;

    //relacion muchos a uno con la tabla status
    @Column(name = "status", columnDefinition = "TINYINT(1)")
    private boolean status;

    //relacion uno a muchos con la tabla subcategories
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<BeanSubcategory> subcategories;
}
