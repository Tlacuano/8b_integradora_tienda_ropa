package mx.edu.utez.services_clothing_shop.model.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.transaction_status.BeanTransactionStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class BeanTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_transaction", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idTransaction;

    @Column(name = "total")
    private double total;

    @Column(name = "id_session")
    private String idSession;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "checkout_status")
    private String checkoutStatus;

    //relacion muchos a uno con la tabla de users
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;

    //relacion muchos a uno con la tabla de orders
    @ManyToOne
    @JoinColumn(name = "fk_id_order")
    private BeanOrder order;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanTransactionStatus status;
}
