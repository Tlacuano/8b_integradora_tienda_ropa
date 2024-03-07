package mx.edu.utez.services_clothing_shop.model.payment_card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPaymentCard extends JpaRepository<BeanPaymentCard, UUID>{
    Page<BeanPaymentCard> findAllByPerson_User_Email (String email, Pageable page);
}
