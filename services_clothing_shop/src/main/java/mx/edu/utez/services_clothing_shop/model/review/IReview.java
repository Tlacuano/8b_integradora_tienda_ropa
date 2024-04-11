package mx.edu.utez.services_clothing_shop.model.review;

import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReview extends JpaRepository<BeanReview, UUID> {
    @Query(value = "SELECT br.comment, br.reviewDate, br.assessment FROM BeanReview br WHERE br.orderHasProduct.idOrderProduct = :idOrderProduct")
    List<Object[]> findEssentialReviewInfo(@Param("idOrderProduct") UUID idOrderProduct);

    boolean existsByIdReview(UUID idReview);
    boolean existsByOrderHasProduct_IdOrderProduct(UUID idOrderProduct);
    BeanReview findByIdReview(UUID idReview);

    @Query("SELECT r FROM BeanReview r WHERE r.orderHasProduct.product.idProduct = :idProduct")
    List<BeanReview> findByProductId(UUID idProduct);

    @Query("SELECT r FROM BeanReview r WHERE r.orderHasProduct.order.idOrder = :idOrder")
    BeanReview findByOrderId(UUID idOrder);


}
