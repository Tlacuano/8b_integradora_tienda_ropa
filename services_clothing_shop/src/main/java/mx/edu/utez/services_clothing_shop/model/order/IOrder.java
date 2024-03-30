package mx.edu.utez.services_clothing_shop.model.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface IOrder extends JpaRepository<BeanOrder, UUID> {

    @Query(value = "SELECT  o from BeanOrder o join  BeanAddress a on o.address.idAddress = a.idAddress join BeanPerson p on a.person.idPerson = p.idPerson join BeanUser u on p.user.idUser = u.idUser where u.email = :email")
    Page<BeanOrder> findAllByAddress_Person_User_Email(String email, Pageable page);

    @Query(value = "CALL sp_post_order(:p_user_id, :p_order_date, :p_order_id_address, :p_order_id_payment_card, :p_order_number);", nativeQuery = true)
    Map<String, Object> postOrder(
            @Param("p_user_id") String userId,
            @Param("p_order_date") LocalDate orderDate,
            @Param("p_order_id_address") String orderIdAddress,
            @Param("p_order_id_payment_card") String orderIdPaymentCard,
            @Param("p_order_number") String orderNumber
    );
}
