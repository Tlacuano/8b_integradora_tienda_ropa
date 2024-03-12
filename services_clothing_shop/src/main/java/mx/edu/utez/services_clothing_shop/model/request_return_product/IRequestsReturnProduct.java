package mx.edu.utez.services_clothing_shop.model.request_return_product;

import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IRequestsReturnProduct extends JpaRepository<BeanRequestReturnProduct, UUID> {

    Optional<BeanRequestReturnProduct> findByOrderHasProduct_IdOrderProduct(UUID orderId);

    public interface ReturnStatusProjection {
        BeanRequestStatus getStatus();
    }

    @Query("SELECT r.status FROM BeanRequestReturnProduct r")
    Page<ReturnStatusProjection> findAllStatuses(Pageable pageable);
}
