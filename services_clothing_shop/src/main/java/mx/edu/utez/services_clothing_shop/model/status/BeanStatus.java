package mx.edu.utez.services_clothing_shop.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
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
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_status", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idStatus;

    @Column(name = "status", length = 50)
    private String status;

    //relacion muchos a uno con la tabla de tipos de status
    @ManyToOne
    @JoinColumn(name = "fk_id_type_status")
    private BeanTypeStatus typeStatus;

    //relacion uno a muchos con la tabla de users
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanUser> user;

    //relacion uno a muchos con la tabla de address
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanAddress> address;

    //relacion uno a muchos con la tabla categories
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanCategory> category;

    //relacion uno a muchos con la tabla subcategories
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanSubcategory> subcategory;

    //relacion uno a muchos con la tabla products
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanProduct> product;

    //relacion uno a muchos con la tabla de producct gallery
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanProductGallery> productGallery;

    //relacion uno a muchos con la tabla de orders has products
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanOrderHasProducts> orderHasProducts;

    //relacion uno a muchos con la tabla request sell product
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestSellProduct> requestSellProduct;

    //relacion uno a muchos con la tabla de request data change
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestDataChange> requestDataChange;

    //relacion uno a muchos con la tabla de request return product
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestReturnProduct> requestReturnProduct;

    //relacion uno a muchos con la tabla de request become seller
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanRequestBecomeSeller> requestBecomeSeller;

    //relacion uno a muchos con la tabla de payments cards
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<BeanPaymentCard> paymentsCards;


}
