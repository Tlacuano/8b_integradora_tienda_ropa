package mx.edu.utez.services_clothing_shop.service.role;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.role.IRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class RoleService {

    private final IRole roleRepository;

    public RoleService(IRole roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(rollbackOn = {Exception.class})
    public BeanRole getRoleById(UUID id) {
        return roleRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public List<BeanRole> getRolesByEmail() {
        return roleRepository.findAll();
    }


}
