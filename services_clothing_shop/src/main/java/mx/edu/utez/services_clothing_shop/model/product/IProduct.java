package mx.edu.utez.services_clothing_shop.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProduct extends JpaRepository<BeanProduct, UUID> {
    Page<BeanProduct> findAllByUser_Email(String email, Pageable page);
    boolean existsByIdProduct(UUID idProduct);

    BeanProduct findByIdProduct(UUID idProduct);
}
