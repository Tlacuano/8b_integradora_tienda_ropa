package mx.edu.utez.services_clothing_shop.model.order_has_products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders_has_products")
@EntityListeners(AuditEntityListener.class)
public class BeanOrderHasProducts {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_order_product", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idOrderProduct;

    @Column(name = "amount")
    private int amount;

    //relacion muchos a uno con la tabla de orders
    @ManyToOne
    @JoinColumn(name = "fk_id_order")
    @JsonIgnore
    private BeanOrder order;

    //relacion muchos a uno con la tabla de products
    @ManyToOne
    @JoinColumn(name = "fk_id_product")
    private BeanProduct product;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanOrderStatus status;

    //relacion uno a uno con la tabla de reviews
    @OneToOne(mappedBy = "orderHasProduct")
    @JsonIgnore
    private BeanReview review;

    //relacion uno a muchos con la tabla de requests_return_product
    @OneToMany(mappedBy = "orderHasProduct")
    @JsonIgnore
    private List<BeanRequestReturnProduct> requestReturnProduct;


}
