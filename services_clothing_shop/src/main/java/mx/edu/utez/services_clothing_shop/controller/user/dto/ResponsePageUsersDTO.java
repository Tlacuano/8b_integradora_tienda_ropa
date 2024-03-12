package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponsePageUsersDTO {
    UUID idUser;
    String fullName;
    List<BeanRole> roles;
    boolean status;

    public static ResponsePageUsersDTO fromUser(BeanUser user) {
        ResponsePageUsersDTO responsePageUsersDTO = new ResponsePageUsersDTO();
        responsePageUsersDTO.setIdUser(user.getIdUser());
        responsePageUsersDTO.setFullName(user.getPerson().getName() + " " + user.getPerson().getLastName() + " " + user.getPerson().getSecondLastName());
        responsePageUsersDTO.setRoles(user.getRoles().stream().map(BeanUserRoles::getRole).toList());
        responsePageUsersDTO.setStatus(user.isStatus());
        return responsePageUsersDTO;
    }
}
