package mx.edu.utez.services_clothing_shop.model.subcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISubCategory extends JpaRepository<BeanSubcategory, UUID> {
    BeanSubcategory findByIdSubcategory(UUID idSubcategory);
    interface SubcategoryNameProjection {
        String getSubcategory();
    }
    @Query("SELECT s FROM BeanSubcategory s WHERE s.category.category = :categoryName")
    List<SubcategoryNameProjection> findByCategoryName(String categoryName);

    Page<BeanSubcategory> findBySubcategoryStartsWithIgnoreCase(String subcategory, Pageable pageable);
}
