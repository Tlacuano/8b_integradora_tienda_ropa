package mx.edu.utez.services_clothing_shop.model.request_become_seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRequestsBecomeSeller extends JpaRepository<BeanRequestsBecomeSeller, UUID>{
    Optional<BeanRequestsBecomeSeller> findByUserEmail(String email);
}