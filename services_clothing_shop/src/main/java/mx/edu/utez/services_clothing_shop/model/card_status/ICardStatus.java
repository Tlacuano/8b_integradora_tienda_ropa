package mx.edu.utez.services_clothing_shop.model.card_status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICardStatus extends JpaRepository<BeanCardStatus, UUID> {
    BeanCardStatus findByStatus(String status);
}
