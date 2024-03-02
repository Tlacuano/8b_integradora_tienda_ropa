package mx.edu.utez.services_clothing_shop.model.payment_card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "payment_cards")
@Entity
public class BeanPaymentCard {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_payment_card", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_payment_card;

    @Column(name = "cardholder_name", length = 100)
    private String cardholder_name;

    @Column(name = "card_number", length = 30)
    private String card_number;

    @Column(name = "expiration_date", length = 5)
    private String expiration_date;

    @Column(name="cvv", length = 3)
    private String cvv;

    //relacion muchos a uno con la tabla de person
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanPerson person;

    //relacion uno a muchos con la tabla de orders
    @OneToMany(mappedBy = "payment_card")
    private List<BeanOrder> orders;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;
}
