package mx.edu.utez.services_clothing_shop.security.controller.dtos;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;

import java.util.List;

@Data
public class AuthUserDTO {
    private String email;
    private String password;
    private boolean status;
    private List<BeanRole> roles;

    public AuthUserDTO() {
    }

    public AuthUserDTO(BeanUser user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles().stream().map(BeanUserRoles::getRole).toList();
    }

}
