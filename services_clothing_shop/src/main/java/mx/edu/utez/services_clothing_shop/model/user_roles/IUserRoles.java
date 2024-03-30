package mx.edu.utez.services_clothing_shop.model.user_roles;

import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoles extends JpaRepository<BeanUserRoles, Long> {
    void deleteByUser(BeanUser user);
}
