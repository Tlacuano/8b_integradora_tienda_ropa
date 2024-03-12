package mx.edu.utez.services_clothing_shop.model.product_gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductGallery extends JpaRepository<BeanProductGallery, UUID> {
    @Query(value = "CALL sp_post_product_gallery(:idProduct, :idStatus: :image)", nativeQuery = true)
    BeanProductGallery postProductGallery(String idProduct, String idStatus, String image);
}
