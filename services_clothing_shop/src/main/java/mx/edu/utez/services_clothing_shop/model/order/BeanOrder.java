package mx.edu.utez.services_clothing_shop.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.oder_has_products.BeanOrderHasProducts;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class BeanOrder {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_order", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_order;

    //relacion muchos a uno con la tabla de address
    @ManyToOne
    @JoinColumn(name = "fk_id_address")
    private BeanAddress address;

    //relacion uno a muchos con la tabla de orders_has_products
    @OneToMany(mappedBy = "order")
    private List<BeanOrderHasProducts> order_has_products;


}
