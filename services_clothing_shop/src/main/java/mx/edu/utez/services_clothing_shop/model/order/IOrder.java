package mx.edu.utez.services_clothing_shop.model.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrder extends JpaRepository<BeanOrder, UUID> {
    Page<BeanOrder> findAllByAddress_Person_User_Email (String email, Pageable page);
}
