package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user_roles.BeanUserRoles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResponsePageUsersDTO {
    UUID idUser;
    String fullName;
    String email;
    String roles;
    String picture;
    boolean status;

    public static ResponsePageUsersDTO fromUser(BeanUser user) {
        ResponsePageUsersDTO responsePageUsersDTO = new ResponsePageUsersDTO();
        responsePageUsersDTO.setIdUser(user.getIdUser());
        responsePageUsersDTO.setEmail(user.getEmail());
        responsePageUsersDTO.setFullName(user.getPerson()
                .getName() + " " + user.getPerson().getLastName() + " " + user.getPerson().getSecondLastName());

        responsePageUsersDTO.setRoles(user.getRoles()
                .stream().map(BeanUserRoles::getRole)
                .map(BeanRole::getRoleName)
                .collect(Collectors.joining("/")));

        responsePageUsersDTO.setStatus(user.isStatus());
        responsePageUsersDTO.setPicture(user.getPerson().getPicture());
        return responsePageUsersDTO;
    }
}
