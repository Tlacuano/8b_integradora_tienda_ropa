package mx.edu.utez.services_clothing_shop.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.transaction.BeanTransaction;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditEntityListener.class)
@Table(name = "users")
public class BeanUser {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idUser;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "verification_code", length = 255)
    private String verificationCode;

    @Column(name = "email_verified", columnDefinition = "TINYINT(0)")
    private boolean emailVerified;

    @Column(name = "privacy_policy", columnDefinition = "TINYINT(0)")
    private boolean privacyPolicy;

    //relacion muchos a muchos con la tabla roles
    @OneToMany(mappedBy = "user")
    private List<BeanUserRoles> roles;

    //relacion uno a uno con la tabla people
    @OneToOne(mappedBy = "user")
    private BeanPerson person;

    //relacion muchos a uno con la tabla status
    @Column(name = "status", columnDefinition = "TINYINT(1)")
    private boolean status;

    //relacion uno a muchos con la tabla de products
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<BeanProduct> products;

    //relacion uno a muchos con la tabla de wish_list
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BeanWishList> wishList;

    //relacion uno a muchos con la tabla de shopping_car
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BeanShoppingCart> shoppingCart;

    //relacion uno a muchos con la tabla de request data change
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BeanRequestDataChange> requestDataChange;

    //relacion uno a muchos con la tabla de request becom seller
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BeanRequestsBecomeSeller> requestBecomeSeller;

    //relacion uno a muchos con la tabla de payment_cards
    @OneToMany(mappedBy = "user")
    private List<BeanPaymentCard> paymentCards;

    //relacion uno a muchos con la tabla de transactions
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BeanTransaction> transactions;
}
