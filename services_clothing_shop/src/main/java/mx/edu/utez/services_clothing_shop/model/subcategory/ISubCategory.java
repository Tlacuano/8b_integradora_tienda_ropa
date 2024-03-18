package mx.edu.utez.services_clothing_shop.model.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISubCategory extends JpaRepository<BeanSubcategory, UUID> {
    BeanSubcategory findByIdSubcategory(UUID idSubcategory);
    boolean findBySubcategory(String subcategory);
    boolean existsByIdSubcategory(UUID idSubcategory);
}
