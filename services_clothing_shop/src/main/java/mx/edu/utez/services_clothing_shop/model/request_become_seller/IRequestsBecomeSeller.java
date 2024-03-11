package mx.edu.utez.services_clothing_shop.model.request_become_seller;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IRequestsBecomeSeller extends JpaRepository<BeanRequestsBecomeSeller, UUID>{
    Optional<BeanRequestsBecomeSeller> findByUserEmail(String email);

    public interface StatusProjection {
        BeanRequestStatus getStatus();
    }

    @Query("SELECT r.status FROM BeanRequestsBecomeSeller r")
    Page<StatusProjection> findAllStatuses(Pageable pageable);
}