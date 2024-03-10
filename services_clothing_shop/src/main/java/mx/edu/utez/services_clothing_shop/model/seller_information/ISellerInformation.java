package mx.edu.utez.services_clothing_shop.model.seller_information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISellerInformation extends JpaRepository<BeanSellerInformation, UUID> {
    boolean existsByIdSellerInformation(UUID idSelllerInformation);
    BeanSellerInformation findByIdSellerInformation(UUID idSellerInformation);
}
