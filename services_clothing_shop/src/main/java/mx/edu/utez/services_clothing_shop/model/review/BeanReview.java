package mx.edu.utez.services_clothing_shop.model.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.oder_has_products.BeanOrderHasProducts;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reviews")
public class BeanReview {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_review", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_review;

    @Column(name="comment", length = 255)
    private String comment;

    @Column(name="review_date")
    private LocalDate review_date;

    @Column(name="assessment")
    private int assessment;

    //relacion uno a uno (el id aqqui)
    @OneToOne
    @JoinColumn(name = "fk_id_order_product")
    private BeanOrderHasProducts order_has_product;
}
