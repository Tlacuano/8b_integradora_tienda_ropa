package mx.edu.utez.services_clothing_shop.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;
import mx.edu.utez.services_clothing_shop.model.people.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class BeanUser {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_user;

    //relacion muchos a muchos con la tabla roles
    @OneToMany(mappedBy = "user")
    private List<BeanUserRoles> roles;

    //relacion uno a uno con la tabla people
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private BeanPerson person;

    //relacion muchos a uno con la tabla status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion uno a muchos con la tabla de products
    @OneToMany(mappedBy = "user")
    private List<BeanProduct> products;
    
    //relacion uno a muchos con la tabla de wish_list
    @OneToMany(mappedBy = "user")
    private List<BeanWishList> wish_list;






}
