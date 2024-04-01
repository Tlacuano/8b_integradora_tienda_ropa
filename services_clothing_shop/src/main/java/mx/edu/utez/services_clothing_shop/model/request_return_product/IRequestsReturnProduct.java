package mx.edu.utez.services_clothing_shop.model.request_return_product;

import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    Optional<ReturnProductInfoProjection> findReturnProductInfoById(@Param("idRequestReturnProduct") UUID idRequestReturnProduct);
}