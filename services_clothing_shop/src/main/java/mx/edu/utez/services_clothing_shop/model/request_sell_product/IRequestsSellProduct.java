package mx.edu.utez.services_clothing_shop.model.request_sell_product;

import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRequestsSellProduct extends JpaRepository<BeanRequestSellProduct, UUID> {

    interface RequestSellStatusProjection {
        BeanRequestStatus getStatus();
    }

    @Query("SELECT r.status FROM BeanRequestSellProduct r")
    Page<RequestSellStatusProjection> findAllStatuses(Pageable pageable);

}
