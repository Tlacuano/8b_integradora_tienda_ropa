package mx.edu.utez.services_clothing_shop.model.user;

import mx.edu.utez.services_clothing_shop.model.user.projections.IGetPageUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUser extends JpaRepository<BeanUser, UUID> {

    Page<IGetPageUsers> findAllBy (Pageable pageable);
}
