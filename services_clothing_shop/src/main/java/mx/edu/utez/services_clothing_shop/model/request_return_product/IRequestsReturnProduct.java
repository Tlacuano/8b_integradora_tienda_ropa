package mx.edu.utez.services_clothing_shop.model.request_return_product;

import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRequestsReturnProduct extends JpaRepository<BeanRequestReturnProduct, UUID> {


    interface ReturnRequestProjection  {
        UUID getIdRequestReturnProduct();
        String getStatus();
        LocalDate getOrderDate();
        String getOrderNumber();
    }

    public interface ReturnProductInfoProjection {
        UUID getIdRequestReturnProduct();
        String getReturnReason();
        UUID getIdOrderProduct();
        UUID getIdOrder();
        String getOrderNumber();
        Integer getAmount();
        String getEmail();
        String getImage();
        Double getPrice();
        String getProductName();
    }


    @Query("SELECT r.idRequestReturnProduct as idRequestReturnProduct, " +
            "r.status.status as status, " +
            "r.orderHasProduct.order.orderDate as orderDate, " +
            "r.orderHasProduct.order.orderNumber as orderNumber " +
            "FROM BeanRequestReturnProduct r " +
            "WHERE r.orderHasProduct.order.orderNumber LIKE %:searchTerm%")
    Page<ReturnRequestProjection> findRequestsWithOrderInfo(Pageable pageable, @Param("searchTerm") String searchTerm);

    @Query("SELECT " +
            "r.idRequestReturnProduct as idRequestReturnProduct, " +
            "r.returnReason as returnReason, " +
            "ohp.idOrderProduct as idOrderProduct, " +
            "o.idOrder as idOrder, " +
            "o.orderNumber as orderNumber, " +
            "ohp.amount as amount, " +
            "u.email as email, " +
            "rp.image as image, " +
            "p.price as price, " +
            "p.productName as productName " +
            "FROM BeanRequestReturnProduct r " +
            "JOIN r.orderHasProduct ohp " +
            "JOIN ohp.order o " +
            "JOIN o.address a " +
            "JOIN a.person per " +
            "JOIN per.user u " +
            "JOIN ohp.product p " +
            "JOIN r.returnProductGallery rp " +
            "WHERE r.idRequestReturnProduct = :idRequestReturnProduct " +
            "AND rp.image IS NOT NULL " +
            "GROUP BY rp.image")
    List<ReturnProductInfoProjection> findReturnProductInfoById(@Param("idRequestReturnProduct") UUID idRequestReturnProduct);

    @Query("SELECT ohp " +
            "FROM BeanOrderHasProducts ohp " +
            "JOIN ohp.order o " +
            "WHERE o.orderNumber = :orderNumber " +
            "ORDER BY ohp.idOrderProduct ASC")
    Optional<BeanOrderHasProducts> findFirstByOrderNumber(@Param("orderNumber") String orderNumber);

    boolean existsByOrderHasProduct_Order_OrderNumberAndStatus_Status(String orderNumber, String status);

    @Query("SELECT rs FROM BeanRequestStatus rs WHERE rs.status = :status")
    Optional<BeanRequestStatus> findRequestStatusByName(@Param("status") String status);

    @Query("SELECT os FROM BeanOrderStatus os WHERE os.status = :status")
    Optional<BeanOrderStatus> findOrderStatusByName(@Param("status") String status);
    @Modifying
    @Query("UPDATE BeanOrderHasProducts ohp SET ohp.status = :status WHERE ohp.order.orderNumber = :orderNumber")
    void updateOrderHasProductsStatus(@Param("status") BeanOrderStatus status, @Param("orderNumber") String orderNumber);
}