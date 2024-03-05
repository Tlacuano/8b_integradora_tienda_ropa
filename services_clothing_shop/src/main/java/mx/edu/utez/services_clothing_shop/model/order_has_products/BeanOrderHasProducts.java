package mx.edu.utez.services_clothing_shop.model.order_has_products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders_has_products")
public class BeanOrderHasProducts {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_order_product", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idOrderProduct;


    @Column(name = "amount")
    private int amount;

    //relacion muchos a uno con la tabla de orders
    @ManyToOne
    @JoinColumn(name = "fk_id_order")
    private BeanOrder order;

    //relacion muchos a uno con la tabla de products
    @ManyToOne
    @JoinColumn(name = "fk_id_product")
    private BeanProduct product;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion uno a uno con la tabla de reviews
    @OneToOne(mappedBy = "order_has_product") // TODO: Fix column not found
    private BeanReview review;

    //relacion uno a muchos con la tabla de requests_return_product
    @OneToMany(mappedBy = "order_has_product") // TODO: Fix column not found
    private List<BeanRequestReturnProduct> requestReturnProduct;
}
