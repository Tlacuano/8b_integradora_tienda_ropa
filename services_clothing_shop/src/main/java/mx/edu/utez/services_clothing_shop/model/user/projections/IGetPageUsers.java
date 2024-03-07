package mx.edu.utez.services_clothing_shop.model.user.projections;

import java.util.List;
import java.util.UUID;

public interface IGetPageUsers {
    UUID getIdUser();
    String getEmail();
    IGetPageUsersUserRolesProjection getRoles();

    interface IGetPageUsersUserRolesProjection {
        List<IGetPageUsersRolesProjection> getRole();
        interface IGetPageUsersRolesProjection {
            String getRoleName();
        }
    }
}
