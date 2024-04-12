package mx.edu.utez.services_clothing_shop.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "categories")
@Entity
@EntityListeners(AuditEntityListener.class)
public class BeanCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_category", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idCategory;

    @Column(name = "category", length = 30)
    private String category;

    @Column(name = "image")
    private String image;

    //relacion muchos a uno con la tabla status
    @Column(name = "status", columnDefinition = "TINYINT(1)")
    private boolean status;

    //relacion uno a muchos con la tabla subcategories
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<BeanSubcategory> subcategories;
}
