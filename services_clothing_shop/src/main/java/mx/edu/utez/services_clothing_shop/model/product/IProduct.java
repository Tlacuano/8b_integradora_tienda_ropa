package mx.edu.utez.services_clothing_shop.model.product;

import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProduct extends JpaRepository<BeanProduct, UUID> {
    List<BeanProduct> findByUser(BeanUser user);

    boolean existsByIdProduct(UUID idProduct);

    BeanProduct findByIdProduct(UUID idProduct);
}
