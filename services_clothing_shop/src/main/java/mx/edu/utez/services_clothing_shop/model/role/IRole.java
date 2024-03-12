package mx.edu.utez.services_clothing_shop.model.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRole extends JpaRepository<BeanRole, UUID> {
}
