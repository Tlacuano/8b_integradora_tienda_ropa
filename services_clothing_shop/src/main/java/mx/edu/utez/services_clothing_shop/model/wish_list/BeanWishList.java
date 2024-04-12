package mx.edu.utez.services_clothing_shop.model.wish_list;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.listener.AuditEntityListener;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditEntityListener.class)
@Table(name = "wish_list")
public class BeanWishList {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_wish", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idWish;

    @Column(name = "amount")
    private int amount;

    //relacion muchos a uno con la tabla de products
    @ManyToOne
    @JoinColumn(name = "fk_id_product")
    private BeanProduct product;

    //relacion muchos a uno con la tabla de users
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;


}
