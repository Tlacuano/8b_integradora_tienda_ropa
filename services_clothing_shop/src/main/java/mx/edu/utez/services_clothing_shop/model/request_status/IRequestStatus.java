package mx.edu.utez.services_clothing_shop.model.request_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRequestStatus extends JpaRepository<BeanRequestStatus, UUID> {
    Optional<BeanRequestStatus> findByStatus(String status);
}