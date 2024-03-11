package mx.edu.utez.services_clothing_shop.model.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAddress extends JpaRepository<BeanAddress, UUID> {
    boolean existsByIdAddress(UUID idAddress);
    BeanAddress findByIdAddress(UUID idAddress);
}
