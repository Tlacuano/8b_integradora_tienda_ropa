package mx.edu.utez.services_clothing_shop.model.product_gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface IProductGallery extends JpaRepository<BeanProductGallery, UUID> {
    @Query(value = "CALL sp_post_product_gallery(:p_product_id, p_image_url_1, p_image_url_2, p_image_url_3, p_image_url_4, p_image_url_5)", nativeQuery = true)
    List<BeanProductGallery> postProductGalley(
            @Param("p_product_id") UUID idProduct,
            @Param("p_image_url_1") String imageUrl1,
            @Param("p_image_url_2") String imageUrl2,
            @Param("p_image_url_3") String imageUrl3,
            @Param("p_image_url_4") String imageUrl4,
            @Param("p_image_url_5") String imageUrl5
    );
}
