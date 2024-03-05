package mx.edu.utez.services_clothing_shop.model.address;

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
@Entity
@Table(name = "address")
public class BeanAddress {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_address", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idAddress;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "references_addres", length = 255)
    private String referencesAddress;

    @Column(name = "postal_code", length = 5)
    private String postalCode;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "cologne", length = 50)
    private String neighborhood;

    //reaccion uno a muchos con la tabla de people
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanPerson person;

    //relacion muchos a uno con la tabla de estados
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion uno a muchos con la tabla de orders
    @OneToMany(mappedBy = "address")
    private List<BeanOrder> orders;
}
