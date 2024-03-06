package mx.edu.utez.services_clothing_shop.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUser extends JpaRepository<BeanUser, UUID> {
}
