package mx.edu.utez.services_clothing_shop.model.seller_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.people.BeanPeople;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sellers_information")
@Entity
public class BeanSellerInformation {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_seller_information", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_seller_information;

    //relacion uno a uno con la tabla people
    @OneToOne
    @MapsId
    @JoinColumn(name = "fk_id_user")
    private BeanPeople person;
}
