package mx.edu.utez.services_clothing_shop.model.order_has_products;

import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrderHasProducts extends JpaRepository<BeanOrderHasProducts, UUID> {
    List<BeanOrderHasProducts> findAllByOrder_IdOrder(UUID idOrder);


    @Query("SELECT ohp FROM BeanOrderHasProducts  ohp JOIN BeanProduct p ON ohp.product.idProduct = p.idProduct " +
            "JOIN BeanUser u ON p.user.idUser = u.idUser WHERE u.email = :email")
    List<BeanOrderHasProducts> findBySeller(String email);

    @Query("SELECT ohp FROM BeanOrderHasProducts  ohp JOIN BeanProduct p ON ohp.product.idProduct = p.idProduct " +
            "JOIN BeanUser u ON p.user.idUser = u.idUser WHERE u.email = :email " +
            "AND ohp.status = :status")
    Page<BeanOrderHasProducts> findAllBySellerAndStatus(String email, BeanOrderStatus status, Pageable pageable);

    @Query("select ohp from BeanOrderHasProducts ohp " +
            " join BeanProduct p on ohp.product.idProduct = p.idProduct " +
            " where p.idProduct = :idProduct " +
            " and ohp.order.address.person.user.idUser = :idBuyer" +
            " and ohp.status != 'Sin pagar'")
    BeanOrderHasProducts findOrderHasProductByBuyerAndProduct(UUID idBuyer, UUID idProduct);

    @Procedure("sp_fulfill_order")
    void sp_fulfill_order(UUID idOrder, UUID idUser);
}
