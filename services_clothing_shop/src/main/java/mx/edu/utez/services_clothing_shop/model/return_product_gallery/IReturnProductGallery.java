package mx.edu.utez.services_clothing_shop.model.return_product_gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReturnProductGallery extends JpaRepository<BeanReturnProductGallery, UUID> {
    boolean existsByIdImage(UUID idImage);
    BeanReturnProductGallery findByIdImage(UUID idImage);
}
