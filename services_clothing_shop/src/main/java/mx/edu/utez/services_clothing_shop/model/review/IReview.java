package mx.edu.utez.services_clothing_shop.model.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReview extends JpaRepository<BeanReview, UUID> {
    boolean existsByIdReview(UUID idReview);
    BeanReview findByIdReview(UUID idReview);

}
