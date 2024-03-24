package mx.edu.utez.services_clothing_shop.model.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUser extends JpaRepository<BeanUser, UUID> {
    boolean existsByEmail(String email);

    @Query("SELECT u FROM BeanUser u WHERE u.idUser NOT IN (" +
            "SELECT ur.user.idUser FROM BeanUserRoles ur WHERE ur.role.roleName IN ('ROLE_ADMIN', 'ROLE_SUPERADMIN')) " +
            "ORDER BY u.status DESC")
    Page<BeanUser> findAllByOrderByStatusDesc (Pageable pageable);

    @Query("SELECT u FROM BeanUser u JOIN u.roles ur WHERE ur.role.roleName = 'ROLE_ADMIN'" +
            "ORDER BY u.status DESC")
    Page<BeanUser> findAllAdminsByOrderByStatusDesc (Pageable pageable);

    BeanUser findByEmail(String email);

    @Query(value = "CALL sp_post_role_user(:p_role_id, :p_user_id)", nativeQuery = true)
    boolean postRoleUser(
            @Param("p_role_id") String roleId,
            @Param("p_user_id") String userId
    );

    @Query(value = "CALL sp_delete_user(:p_email)", nativeQuery = true)
    String deleteAccount(@Param("p_email") String email);

}
