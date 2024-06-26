package mx.edu.utez.services_clothing_shop.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
@EntityListeners(AuditEntityListener.class)
public class BeanOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_order", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idOrder;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "order_number", length = 100, nullable = false, unique = true, updatable = false)
    private String orderNumber;

    //relacion muchos a uno con la tabla de address
    @ManyToOne
    @JoinColumn(name = "fk_id_address", nullable = false, updatable = false)
    private BeanAddress address;

    //relacion uno a muchos con la tabla de orders_has_products
    @OneToMany(mappedBy = "order")
    private List<BeanOrderHasProducts> orderHasProducts;

    //relacion muchos a uno con la tabla de payment_cards
    @ManyToOne
    @JoinColumn(name = "fk_id_payment_card", nullable = false, updatable = false)
    private BeanPaymentCard paymentCard;
}
