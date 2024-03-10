package mx.edu.utez.services_clothing_shop.model.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICategory extends JpaRepository<BeanCategory, UUID> {
    boolean existsByIdCategory(UUID idCategory);
    BeanCategory findByIdCategory(UUID category);
}