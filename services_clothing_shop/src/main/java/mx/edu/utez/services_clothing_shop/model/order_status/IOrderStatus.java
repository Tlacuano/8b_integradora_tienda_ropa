package mx.edu.utez.services_clothing_shop.model.order_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderStatus extends JpaRepository<BeanOrderStatus, UUID> {
    BeanOrderStatus findByStatus(String status);
}
