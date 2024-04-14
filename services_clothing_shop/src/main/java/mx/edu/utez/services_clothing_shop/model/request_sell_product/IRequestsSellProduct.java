package mx.edu.utez.services_clothing_shop.model.request_sell_product;

import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IRequestsSellProduct extends JpaRepository<BeanRequestSellProduct, UUID> {

    @Query("SELECT r FROM BeanRequestSellProduct r WHERE r.product.user.email = :email")
    List<BeanRequestSellProduct> findAllByUser (String email);

    interface RequestSellStatusProjection {
        UUID getIdRequestSellProduct();
        String getImage();
        String getProductName();
        String getEmail();
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


    @Query("SELECT r.idRequestSellProduct as idRequestSellProduct, " +
            "(SELECT pg.image FROM BeanProductGallery pg WHERE pg.product = p AND pg.status.status = 'Principal') as image, " +
            "p.productName as productName, " +
            "u.email as email, " +
            "r.status as status " +
            "FROM BeanRequestSellProduct r " +
            "JOIN r.product p " +
            "JOIN p.user u")
    Page<RequestSellStatusProjection> findAllStatuses(Pageable pageable);




    @Query("SELECT r.idRequestSellProduct as idRequestSellProduct, u.email as userEmail, p.price as price, p.description as description, p.productName as productName, pg.image as image, p.idProduct as productId " +
            "FROM BeanRequestSellProduct r " +
            "INNER JOIN r.product p " +
            "INNER JOIN p.user u " +
            "INNER JOIN p.productGallery pg " +
            "WHERE r.idRequestSellProduct = :id")
    RequestDetailsProjection findRequestDetailsById(@Param("id") UUID id);

    @Query("SELECT r.idRequestSellProduct as idRequestSellProduct, " +
            "(SELECT pg.image FROM BeanProductGallery pg WHERE pg.product = p AND pg.status.status = 'Principal') as image, " +
            "p.productName as productName, " +
            "u.email as email, " +
            "r.status as status " +
            "FROM BeanRequestSellProduct r " +
            "JOIN r.product p " +
            "JOIN p.user u " +
            "WHERE u.email LIKE :email")
    Page<RequestSellStatusProjection> findAllByEmailLikeIgnoreCase(@Param("email") String email,Pageable pageable);
}


