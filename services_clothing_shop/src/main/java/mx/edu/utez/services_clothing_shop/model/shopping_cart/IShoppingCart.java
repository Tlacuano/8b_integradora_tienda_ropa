package mx.edu.utez.services_clothing_shop.model.shopping_cart;

import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IShoppingCart extends JpaRepository<BeanShoppingCart,UUID> {
    List<BeanShoppingCart> findAllByUser_Email(String userEmail);

    boolean existsByProductAndUser (BeanProduct product, BeanUser user);
}
