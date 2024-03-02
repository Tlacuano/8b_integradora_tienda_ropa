package mx.edu.utez.services_clothing_shop.model.status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.oder_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.type_status.BeanTypeStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "status")
public class BeanStatus {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_status", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id_status;

    @Column(name="status", length = 50)
    private String status;

    //relacion muchos a uno con la tabla de tipos de status
    @ManyToOne
    @JoinColumn(name = "fk_id_type_status")
    private BeanTypeStatus type_status;

    //relacion uno a muchos con la tabla de users
    @OneToMany(mappedBy = "status")
    private List<BeanUser> user;

    //relacion uno a muchos con la tabla de address
    @OneToMany(mappedBy = "status")
    private List<BeanAddress> address;

    //relacion uno a muchos con la tabla categories
    @OneToMany(mappedBy = "status")
    private List<BeanCategory> category;

    //relacion uno a muchos con la tabla subcategories
    @OneToMany(mappedBy = "status")
    private List<BeanSubcategory> subcategory;

    //relacion uno a muchos con la tabla products
    @OneToMany(mappedBy = "status")
    private List<BeanProduct> product;

    //relacion uno a muchos con la tabla de producct gallery
    @OneToMany(mappedBy = "status")
    private List<BeanProductGallery> product_gallery;

    //relacion uno a muchos con la tabla de orders has products
    @OneToMany(mappedBy = "status")
    private List<BeanOrderHasProducts> order_has_products;

    //relacion uno a muchos con la tabla request sell product
    @OneToMany(mappedBy = "status")
    private List<BeanRequestSellProduct> request_sell_product;

    //relacion uno a muchos con la tabla de request data change
    @OneToMany(mappedBy = "status")
    private List<BeanRequestDataChange> request_data_change;

    //relacion uno a muchos con la tabla de request return product
    @OneToMany(mappedBy = "status")
    private List<BeanRequestReturnProduct> request_return_product;

    //relacion uno a muchos con la tabla de request become seller
    @OneToMany(mappedBy = "status")
    private List<BeanRequestBecomeSeller> request_become_seller;

    //relacion uno a muchos con la tabla de payments cards
    @OneToMany(mappedBy = "status")
    private List<BeanPaymentCard> payments_cards;



}
