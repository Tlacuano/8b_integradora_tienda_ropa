package mx.edu.utez.services_clothing_shop.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProduct extends JpaRepository<BeanProduct, UUID> {
    Page<BeanProduct> findAllByUser_Email(String email, Pageable page);

    BeanProduct findByIdProduct(UUID idProduct);

    boolean existsByIdProduct(UUID idProduct);

    boolean deleteByIdProduct(UUID idProduct);

    boolean existsByProductName(String productName);

    @Query("SELECT p FROM BeanProduct p WHERE p.subcategory.category.category = :category AND p.status = true")
    List<BeanProduct> findAllByCategory(String category);

    @Query("SELECT p FROM BeanProduct p WHERE p.subcategory.subcategory = :subcategory AND p.status = true AND p.subcategory.category.category = :category")
    List<BeanProduct> findAllBySubcategory(String category, String subcategory);
}
