package mx.edu.utez.services_clothing_shop.model.product_gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductGallery extends JpaRepository<BeanProductGallery, UUID> {
}
