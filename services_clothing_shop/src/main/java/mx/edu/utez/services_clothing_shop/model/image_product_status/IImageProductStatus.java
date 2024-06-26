package mx.edu.utez.services_clothing_shop.model.image_product_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IImageProductStatus extends JpaRepository<BeanImageProductStatus, UUID> {
    BeanImageProductStatus findByStatus(String status);
}
