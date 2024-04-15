package mx.edu.utez.services_clothing_shop.model.product_gallery;

import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductGallery extends JpaRepository<BeanProductGallery, UUID> {
    List<BeanProductGallery> findByProduct(BeanProduct product);
    @Query(value = "CALL sp_post_product_gallery(:p_id_product, :p_image, :p_id_status);", nativeQuery = true)
    public void postProductGallery(
            @Param("p_id_product") String idProduct,
            @Param("p_image") String image,
            @Param("p_id_status") String idStatus
    );
    BeanProductGallery findByImage(String image);
    public void deleteAllByProduct(BeanProduct product);
}
