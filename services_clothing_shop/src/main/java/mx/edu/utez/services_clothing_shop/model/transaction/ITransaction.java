package mx.edu.utez.services_clothing_shop.model.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITransaction extends JpaRepository<BeanTransaction, UUID> {
    BeanTransaction findByIdSession(String idSession);
}
