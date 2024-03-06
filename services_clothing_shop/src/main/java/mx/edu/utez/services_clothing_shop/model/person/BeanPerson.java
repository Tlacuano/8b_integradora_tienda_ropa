package mx.edu.utez.services_clothing_shop.model.person;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "people")
@Entity
public class BeanPerson {
    @Id
    @Column(name = "id_person")
    private UUID id_person;

    //relacion uno a uno con la tabla users, usamos la misma llave primaria
    @OneToOne
    @MapsId
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(name = "second_last_name", length = 30)
    private String secondLastName;

    @Column(name = "picture", length = 100)
    private String picture;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    //relacion uno a uno con la tabla seller_information
    @OneToOne(mappedBy = "person", orphanRemoval = true)
    private BeanSellerInformation sellerInformation;

    //relacion uno a muchos con la tabla de payment_cards
    @OneToMany(mappedBy = "person")
    private List<BeanPaymentCard> paymentCards;
}
