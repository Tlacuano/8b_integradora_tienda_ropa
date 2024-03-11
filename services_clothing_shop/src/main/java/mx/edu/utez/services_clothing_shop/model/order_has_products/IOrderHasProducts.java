package mx.edu.utez.services_clothing_shop.model.order_has_products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderHasProducts extends JpaRepository<BeanOrderHasProducts, String>{
    Page<BeanOrderHasProducts> findAllByOrder_IdOrder(UUID idOrder, Pageable page);
}
