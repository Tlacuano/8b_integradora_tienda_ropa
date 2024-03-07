package mx.edu.utez.services_clothing_shop.service.role;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.role.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class RoleService {

    @Autowired
    private IRole roleRepository;

    @Transactional(rollbackOn = {Exception.class})
    public BeanRole getRoleById(UUID id) {
        return roleRepository.findById(id).get();
    }
}
