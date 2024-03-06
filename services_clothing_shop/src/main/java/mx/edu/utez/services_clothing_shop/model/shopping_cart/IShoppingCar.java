package mx.edu.utez.services_clothing_shop.model.shopping_car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IShoppingCar extends JpaRepository<BeanShopingCar, UUID> {
    List<BeanShopingCar>findByUserEmail(String userEmail);
}
