package mx.edu.utez.services_clothing_shop.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProduct extends JpaRepository<BeanProduct, UUID> {
    Page<BeanProduct> findAllByUser_Email(String email, Pageable page);

    BeanProduct findByIdProduct(UUID idProduct);

    void deleteByIdProduct(UUID idProduct);

    @Query("SELECT p FROM BeanProduct p JOIN p.requestSellProduct s WHERE p.subcategory.category.category = :category AND p.status = true AND p.amount > 0 AND s.status.status = 'Aprobado' AND p.user.email != :email")
    Page<BeanProduct> findAllByCategory(String category, String email, Pageable page);

    @Query("SELECT p FROM BeanProduct p JOIN p.requestSellProduct s WHERE p.subcategory.subcategory = :subcategory AND p.status = true AND p.subcategory.category.category = :category AND p.amount > 0 AND s.status.status = 'Aprobado' AND p.user.email != :email")
    Page<BeanProduct> findAllBySubcategory(String category, String subcategory, String email, Pageable page);

    @Query("SELECT p FROM BeanProduct p JOIN p.requestSellProduct s WHERE p.productName LIKE %:query% AND p.subcategory.subcategory = :subcategory AND p.subcategory.category.category = :category AND p.status = true AND p.amount > 0 AND s.status.status = 'Aprobado' AND p.user.email != :email")
    Page<BeanProduct> findAllByProductNameAndSubcategory(String query, String category, String subcategory, String email, Pageable page);

    @Query("SELECT p FROM BeanProduct p JOIN p.requestSellProduct s WHERE p.productName LIKE %:query% AND p.subcategory.category.category = :category AND p.status = true AND p.amount > 0 AND s.status.status = 'Aprobado' AND p.user.email != :email")
    Page<BeanProduct> findAllByProductNameContainingIgnoreCase(String query, String category, String email, Pageable page);

    Page<BeanProduct> findAllByProductNameLikeIgnoreCaseAndUserEmail(String productName, String userEmail, Pageable page);
}
