package mx.edu.utez.services_clothing_shop.model.shopping_cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IShoppingCart extends JpaRepository<BeanShopingCart,UUID> {
    List<BeanShopingCart> findAllByUser_Email(String userEmail);
}
