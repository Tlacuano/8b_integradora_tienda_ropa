package mx.edu.utez.services_clothing_shop.model.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.people.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "address")
public class BeanAddress {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_address", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_address;

    //reaccion uno a muchos con la tabla de people
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanPerson person;

    //relacion muchos a uno con la tabla de estados
    @ManyToOne
    @JoinColumn(name = "fk_id_state")
    private BeanStatus status;

    //relacion uno a muchos con la tabla de orders
    @OneToMany(mappedBy = "address")
    private List<BeanOrder> orders;
}
