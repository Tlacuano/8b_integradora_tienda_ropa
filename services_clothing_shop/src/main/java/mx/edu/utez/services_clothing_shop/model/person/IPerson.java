package mx.edu.utez.services_clothing_shop.model.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPerson extends JpaRepository<BeanPerson, UUID> {
}
