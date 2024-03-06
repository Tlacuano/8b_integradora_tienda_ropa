package mx.edu.utez.services_clothing_shop.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProduct extends JpaRepository<BeanProduct, UUID> {
}
