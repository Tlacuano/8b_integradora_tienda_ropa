package mx.edu.utez.services_clothing_shop.service.role;

import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.role.IRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    private final IRole iRole;

    public RoleService(IRole iRole) {
        this.iRole = iRole;
    }

    @Transactional
    public BeanRole getRoleByName(String roleName){
        return iRole.findByRoleName(roleName);
    }

}
