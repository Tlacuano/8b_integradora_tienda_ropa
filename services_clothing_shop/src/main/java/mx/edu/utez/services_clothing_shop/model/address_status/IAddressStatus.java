package mx.edu.utez.services_clothing_shop.model.address_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAddressStatus extends JpaRepository<BeanAddressStatus, UUID> {
    Optional<BeanAddressStatus> findByIdStatus(UUID idStatus);
}
