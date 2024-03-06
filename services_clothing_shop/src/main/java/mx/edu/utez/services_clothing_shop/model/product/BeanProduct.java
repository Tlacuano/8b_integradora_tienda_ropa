package mx.edu.utez.services_clothing_shop.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShopingCart;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class BeanProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_product", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID idProduct;

    @Column(name = "product_name", length = 30)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    //relacion muchos a uno con la tabla de subcategories
    @ManyToOne
    @JoinColumn(name = "fk_id_subcategory")
    private BeanSubcategory subcategory;

    //relacion muchos a uno con la tabla de status
    @ManyToOne
    @JoinColumn(name = "fk_id_status")
    private BeanStatus status;

    //relacion muchos a uno con la tabla de users
    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private BeanUser user;

    //rekacuib ybi a muchos con la tabla de product_gallery
    @OneToMany(mappedBy = "product")
    private List<BeanProductGallery> productGallery;

    //relacion uno a muchos con la tabla order has products
    @OneToMany(mappedBy = "product")
    private List<BeanOrderHasProducts> orderHasProducts;

    //relacion uno a muchos con la tabla de wishlists
    @OneToMany(mappedBy = "product")
    private List<BeanWishList> wishList;

    //relacion uno a muchos con la tabla de shopping_cart
    @OneToMany(mappedBy = "product")
    private List<BeanShopingCart> shoppingCart;

    //relacion uno a muchos con la tabla request sell product
    @OneToMany(mappedBy = "product")
    private List<BeanRequestSellProduct> requestSellProduct;

}
