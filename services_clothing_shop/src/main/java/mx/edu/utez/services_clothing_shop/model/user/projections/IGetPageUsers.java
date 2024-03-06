package mx.edu.utez.services_clothing_shop.model.user.projections;

import mx.edu.utez.services_clothing_shop.model.role.BeanRole;

import java.util.List;

public interface IGetPageUsers {
    String getEmail();
    UserRolesProjection getRoles();

    interface UserRolesProjection {
        List<RolesProjection> getRole();

        interface RolesProjection {
            String getRoleName();
        }
    }
}
