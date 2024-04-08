package mx.edu.utez.services_clothing_shop.model.payment_card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.card_status.BeanCardStatus;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;

import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "payment_cards")
@Entity
@EntityListeners(AuditEntityListener.class)
public class BeanPaymentCard {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_payment_card", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idPaymentCard;

    @Column(name = "cardholder_name", nullable = false, updatable = false)
    private String cardholderName;

    @Column(name = "card_number", nullable = false, updatable = false)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false, updatable = false)
    private String expirationDate;

    @Column(name = "cvv")
    private String cvv;

    //relacion muchos a uno con la tabla de users
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    @JsonIgnore
    private BeanUser user;

    //relacion uno a muchos con la tabla de orders
    @OneToMany(mappedBy = "paymentCard")
    @JsonIgnore
    private List<BeanOrder> orders;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanCardStatus status;
}
