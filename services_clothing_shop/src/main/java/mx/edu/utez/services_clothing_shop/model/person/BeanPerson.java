package mx.edu.utez.services_clothing_shop.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "people")
@Entity
@EntityListeners(AuditEntityListener.class)
public class BeanPerson {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_person", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idPerson;

    //relacion uno a uno con la tabla users, usamos la misma llave primaria
    @OneToOne
    @JoinColumn(name = "fk_id_user")
    @JsonIgnore
    private BeanUser user;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @Column(name = "picture")
    private String picture;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;



    @Column(name = "verification_phone", columnDefinition = "TINYINT(1)")
        private boolean verificationPhone;

    //relacion uno a uno con la tabla seller_information
    @OneToOne(mappedBy = "person", orphanRemoval = true)
    private BeanSellerInformation sellerInformation;

    //relacion uno a muchos con la tabla address
    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private List<BeanAddress> addresses;

}
