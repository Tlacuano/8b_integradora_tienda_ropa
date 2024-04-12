package mx.edu.utez.services_clothing_shop.model.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reviews")
@EntityListeners(AuditEntityListener.class)
public class BeanReview {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_review", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idReview;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "assessment")
    private int assessment;

    //relacion uno a uno (el id aqqui)
    @OneToOne
    @JoinColumn(name = "fk_id_order_product")
    @JsonIgnore
    private BeanOrderHasProducts orderHasProduct;
}
