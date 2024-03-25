package mx.edu.utez.services_clothing_shop.model.request_sell_product;

import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRequestsSellProduct extends JpaRepository<BeanRequestSellProduct, UUID> {

    interface RequestSellStatusProjection {
        UUID getIdRequestSellProduct();
        String getImage();
        String getProductName();
        BeanRequestStatus getStatus();
    }


    interface RequestDetailsProjection {
        UUID getIdRequestSellProduct();
        String getUserEmail();
        double getPrice();
        String getDescription();
        String getProductName();
        String getImage();

        UUID getProductId();
    }


    @Query("SELECT r.idRequestSellProduct as idRequestSellProduct, pg.image as image, p.productName as productName, r.status as status " +
            "FROM BeanRequestSellProduct r " +
            "INNER JOIN r.product p " +
            "INNER JOIN p.productGallery pg")
    Page<RequestSellStatusProjection> findAllStatuses(Pageable pageable);


    @Query("SELECT r.idRequestSellProduct as idRequestSellProduct, u.email as userEmail, p.price as price, p.description as description, p.productName as productName, pg.image as image, p.idProduct as productId " +
            "FROM BeanRequestSellProduct r " +
            "INNER JOIN r.product p " +
            "INNER JOIN p.user u " +
            "INNER JOIN p.productGallery pg " +
            "WHERE r.idRequestSellProduct = :id")
    RequestDetailsProjection findRequestDetailsById(@Param("id") UUID id);


}

