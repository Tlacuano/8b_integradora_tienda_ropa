package mx.edu.utez.services_clothing_shop.model.payment_card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IPaymentCard extends JpaRepository<BeanPaymentCard, UUID> {
    Page<BeanPaymentCard> findAllByUser_Email(String email, Pageable page);

    int countByUser_Email(String email);

    boolean existsByCardNumberAndUser_Email(String cardNumber, String email);

    void deleteByCardNumberAndUser_Email(String cardNumber, String email);

    @Query(value = "CALL sp_put_payment_card_status(:p_id_card, :p_status);", nativeQuery = true)
    Map<String, Object> putPaymentCardStatus(
            @Param("p_id_card") String idCard,
            @Param("p_status") String status
    );
}
