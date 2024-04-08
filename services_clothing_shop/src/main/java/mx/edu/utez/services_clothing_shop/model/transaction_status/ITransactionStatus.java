package mx.edu.utez.services_clothing_shop.model.transaction_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITransactionStatus extends JpaRepository<BeanTransactionStatus, UUID> {
    BeanTransactionStatus findByStatus(String status);
}
